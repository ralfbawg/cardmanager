package com.ralf.cardmanager.scheduler;

import com.jeesite.common.cache.CacheUtils;
import com.jeesite.common.utils.SpringUtils;
import com.ralf.cardmanager.budget.entity.TblBudget;
import com.ralf.cardmanager.budget.service.TblBudgetService;
import com.ralf.cardmanager.cardinfo.entity.TblCardInfo;
import com.ralf.cardmanager.cardinfo.service.TblCardInfoService;
import com.ralf.cardmanager.cardtransaction.entity.TblCardTransaction;
import com.ralf.cardmanager.cardtransaction.service.TblCardTransactionService;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardoperation.*;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCardTransactionsByCompanyId;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCardTransactionsByCompanyIdRsp;
import com.ralf.cardmanager.spider.task.divvypay.operation.cardtranscation.GetCompanyTransactionsTotalCount;
import com.ralf.cardmanager.system.CommonService;
import com.ralf.cardmanager.system.SpType;
import com.ralf.cardmanager.system.exception.BudgetNotEnoughException;
import com.ralf.cardmanager.tblbizparam.entity.TblBizParam;
import com.ralf.cardmanager.tblbizparam.service.TblBizParamService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.ralf.cardmanager.system.CommonService.SITE_SWITCH_CACHE;
import static com.ralf.cardmanager.system.CommonService.SITE_SWITCH_CACHE_KEY;
import static com.ralf.cardmanager.system.Constants.PROC_STATUS_FINISH;
import static com.ralf.cardmanager.system.Constants.PROC_STATUS_WAIT;

@Component
@Slf4j
public class SchedulerService {
	public static final Long pageSize = 30l;

	public static final int getCardDetailCount = 100;

	@Autowired
	TblCardInfoService cardInfoService;

	@Autowired
	TblCardTransactionService transactionService;

	@Autowired
	TblBizParamService tblBizParamService;

	@Autowired
	GetCardTransactionsByCompanyId getCardTransactionsByCompanyId;

	@Autowired
	GetCompanyTransactionsTotalCount getCompanyTransactionsTotalCount;

	@Autowired
	UnfreezedCard unfreezedCard;
	@Autowired
	GetCompanyVirtualCards getCompanyVirtualCards;
	@Autowired
	CommonService commonService;
	@Autowired
	GetVirtualCardDetailsInfo getVirtualCardDetailsInfo;
	@Autowired
	GetVirtualCardEditInfo getVirtualCardEditInfo;
	@Autowired
	TblBudgetService budgetService;

	// 更新卡的allcocation
	@Scheduled(cron = "* * * 1 * *")
	public void updateCardAllocationId() {

	}

	// 创建卡
	@Scheduled(fixedRate = 1 * 30 * 1000)
	public void createCard() {
		var cardQuery = new TblCardInfo();
		cardQuery.setCardStatus("tobecreate");
		val list = cardInfoService.findList(cardQuery);
		val ids = list.stream().map(t -> {
			t.setCardStatus("creating");
			return t.getId();
		}).collect(Collectors.toList()).toArray(new String[0]);
		cardQuery.setCardStatus("creating");
		cardQuery.setId_in(ids);
		cardInfoService.update(cardQuery);
		list.stream().forEach(t -> {
			try {
				val cardQuery = new TblCardInfo();
				cardQuery.setCardOwner(t.getCardOwner());
				var count = cardInfoService.findCount(cardQuery);
				val tmpCount = count+1;
				String timestamp = String.valueOf(System.currentTimeMillis());
				String tmpName = t.getCardName().split(",")[0];
				val rsp = SpringUtils.getBean(CreateCardByBudget.class)
						.init(String.valueOf(t.getCardAmount()), timestamp).execute();// 实际卡名用时间戳，好排序
//                t.setBudgetId(rsp.getBudgetId());
				t.setCardId(rsp.getCardId());
				t.setCardStatus(rsp.getStep2Resp().getCardStatus());
				t.setCardToken(rsp.getStep2Resp().getCardToken());
				t.setCardBrand(rsp.getStep2Resp().getBrand());
				t.setCardNo(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getCardNo());
				t.setCvv(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getCvv());
				t.setExp(rsp.getStep2Resp().getStep3GetPanUrlRsp().getCardinfoRsp().getExp());
				t.setCardSpendAmount(0l);
				t.setExpiredDate(rsp.getStep2Resp().getExpirationDate());
				t.setUserAllocationId(rsp.getStep2Resp().getUserAllocation());
				t.setCardType(rsp.getStep2Resp().getCardType());
				t.setNickname(tmpName+tmpCount);
				t.setIsNewRecord(false);
				t.setUpdateDate(new Date());
				cardInfoService.save(t);
				commonService.updateCardCache(t);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	// 自动激活卡
	@Scheduled(cron = "0/30 * * * * *")
	@Transactional
	public void UnfreezedCard() throws Exception {
		val declineTransaction = new TblCardTransaction();
		declineTransaction.setProcStatus(PROC_STATUS_WAIT);
		declineTransaction.setTransactionType("DECLINE");
		val list = transactionService.findList(declineTransaction);
		if (list != null && list.size() > 0) {
			val filterTransactionList = list.stream()
					.filter(t -> commonService.getCardinfoByCardId(t.getCardId()) != null).collect(Collectors.toList());
			for (TblCardTransaction tblCardTransaction : filterTransactionList) {
				TblCardInfo cardInfo = null;
				val rsp = getVirtualCardDetailsInfo.init(tblCardTransaction.getCardId()).execute();
				var result = rsp.isFrozen();
				if (rsp.isFrozen()) {
					val card = new TblCardInfo();
					card.setCardId(tblCardTransaction.getCardId());
					cardInfo = cardInfoService.findList(card).get(0);
					result = unfreezedCard(cardInfo, 0);
				}
				if (result) {
					if (commonService.getUnfreezeCardCount(tblCardTransaction.getCardId()) > 10) {
						tblCardTransaction.setProcStatus(PROC_STATUS_FINISH);
						transactionService.update(tblCardTransaction);
					}
					continue;
				}
				if (!result && tblCardTransaction.getDeclineReason().equalsIgnoreCase("exceeds_vc_balance")) {// 如果是超过余额，需要自动充值

					if (!autoCharge(cardInfo, commonService.getAutoChargeAmount() == 0 ? tblCardTransaction.getAmount()
							: commonService.getAutoChargeAmount())) {
						continue;
					}
				}
				if (result && tblCardTransaction.getDeclineReason().equalsIgnoreCase("exceeds_vc_limit")) {// todo
																											// 如果是超过限额
				}
				tblCardTransaction.setProcStatus(PROC_STATUS_FINISH);
				transactionService.update(tblCardTransaction);

			}
		}
	}

	@Transactional
	public boolean unfreezedCard(TblCardInfo cardinfo, int retry) throws Exception {
		val rsp = unfreezedCard.init(cardinfo.getCardId()).execute();
		if (rsp.isFrozen() && retry < 5) {
			retry++;
			Thread.sleep(1000 * 5);
			unfreezedCard(cardinfo, retry++);
		} else {
			if (!rsp.isFrozen()) {
				cardinfo.setCardStatus("actived");
				cardInfoService.update(cardinfo);
				return rsp.isFrozen();
			} else {
				return rsp.isFrozen();
			}
		}
		return false;
	}

	/**
	 * 自动充值
	 *
	 * @param cardinfo
	 * @throws Exception
	 */
	private boolean autoCharge(TblCardInfo cardinfo, Long amount) {
		try {
			cardInfoService.chargeCard(cardinfo, amount);
			return true;
		} catch (BudgetNotEnoughException e) {
			log.error("card({})自动充值({})失败", cardinfo.getId(), amount);
			e.printStackTrace();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// 自动更新余额与状态
	@Scheduled(cron = "0/30 * * * * *")
	public void updateCardAmount() {
		val cardQuery = new TblCardInfo();
		cardQuery.setUpdateDate_lte(new DateTime().minusMinutes(30).toDate());
		cardQuery.setPageSize(getCardDetailCount);
		var page = cardInfoService.findPage(cardQuery);
		var list = page.getList();
		if (list == null || list.size() <= 0) {
			cardQuery.setUpdateDate_lte(new DateTime().minusMinutes(20).toDate());
			list = cardInfoService.findPage(cardQuery).getList();
		}
		if (list.size() > 0) {
			list.forEach(t -> {
				try {
					val rsp = getVirtualCardEditInfo.init(t.getCardId()).execute();
					val rsp2 = getVirtualCardDetailsInfo.init(t.getCardId()).execute();
					t.setCardAmount(rsp.getAmount());
					t.setCardSpendAmount(rsp.getClearedAmount());
					t.setUserAllocationId(rsp.getAllocationId());
					t.setUpdateDate(new Date());
					t.setCardStatus(rsp2.isFrozen() ? (rsp2.isDeleted() ? "deleted" : "frozen") : "actived");
					cardInfoService.update(t);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		}
		if (page.getCount() / 30 > 3) {
			updateCardAmount();
		}

	}

	@Scheduled(cron = "* 5 * * * *")
	public void budgetProcess() {
		val list = budgetService.findList(new TblBudget());
		list.forEach(t -> {
			val spendAmount = cardInfoService.getClearAmount(t.getId());
			t.setSpendAmount(spendAmount);
			budgetService.update(t);
		});
	}

	/**
	 * 获取正常流水
	 *
	 * @throws IOException
	 */
	@Scheduled(fixedDelay = 60 * 1000) // 每分钟一次
//    @Scheduled(fixedDelay = 30 * 1000)//每分钟一次
	public void GetCardTransactions() throws Exception {
		log.debug("开始执行正常流水获取");
		Long dbTransactionCount = 0l;
		val param = new TblBizParam();
		param.setKey("TransactionsTotal");
		val list = tblBizParamService.findList(param);
		if (list != null && list.size() == 1) {
			dbTransactionCount = Long.valueOf(list.get(0).getValue());

		}
		val transactionTotal = getCompanyTransactionsTotalCount.init(null).execute();
		if (transactionTotal.getTotalCount() != dbTransactionCount) {
			val size = transactionTotal.getTotalCount() - dbTransactionCount;
			if (size > pageSize) {
				for (int i = 0; i < (size % pageSize > 0 ? 1 : 0) + size / pageSize; i++) {
					if (size / pageSize > 10 && i % 10 == 0) {
						Thread.sleep(1000 * 5);
					}
					val rsp = getCardTransactionsByCompanyId.init("", null, null, pageSize, i * pageSize, null)
							.execute();
					try {
						saveTransaction(rsp);
					} catch (DuplicateKeyException e) {
						continue;
					} catch (Exception e) {
						throw e;
					}
				}
			} else {
				val rsp = getCardTransactionsByCompanyId.init("", null, null, pageSize, 0l, null).execute();
				saveTransaction(rsp);
			}

			if (list != null && list.size() == 1) {
				val tmp = list.get(0);
				tmp.setValue(String.valueOf(transactionTotal.getTotalCount()));
				tblBizParamService.update(tmp);
			}
		}

	}

	/**
	 * 获取异常流水
	 *
	 * @throws IOException
	 */
	@Scheduled(fixedDelay = 60 * 1000) // 每分钟一次
	public void GetCardDeclineTransactions() throws Exception {
		log.debug("开始执行异常流水获取");
		Long dbDeclineTransactionCount = 0l;
		val param = new TblBizParam();
		param.setKey("DeclineTransactionsTotal");
		val list = tblBizParamService.findList(param);
		if (list != null && list.size() == 1) {
			dbDeclineTransactionCount = Long.valueOf(list.get(0).getValue());
		}
		val transactionTotal = getCompanyTransactionsTotalCount.init("decline").execute();
		if (transactionTotal.getTotalCount() != dbDeclineTransactionCount) {
			val size = transactionTotal.getTotalCount() - dbDeclineTransactionCount;
			if (size > pageSize) {
				for (int i = 0; i < (size % pageSize > 0 ? 1 : 0) + size / pageSize; i++) {
					if (size / pageSize > 10 && i % 10 == 0) {
						Thread.sleep(1000 * 2);
					}
					val rsp = getCardTransactionsByCompanyId.init("", null, null, pageSize, i * pageSize, "decline")
							.execute();
					saveTransaction(rsp);
				}
			} else {
				val rsp = getCardTransactionsByCompanyId.init("", null, null, pageSize, 0l, "decline").execute();
				saveTransaction(rsp);
			}
			if (list != null && list.size() == 1) {
				val tmp = list.get(0);
				tmp.setValue(String.valueOf(transactionTotal.getTotalCount()));
				tblBizParamService.update(tmp);
			}
		}

	}

	private void saveTransaction(GetCardTransactionsByCompanyIdRsp rsp) {
		rsp.getList().forEach(t -> {
			val transaction = new TblCardTransaction();
			transaction.setIsNewRecord(true);
			transaction.setSpTransactionId(t.getTransactionId());
			transaction.setAmount(t.getAmount());
			transaction.setCardId(t.getCardId());
			transaction.setClearedDate(new DateTime(t.getClearDate()).toString("yyyy-MM-dd HH:mm:ss"));
			transaction.setOccurredDate(new DateTime(t.getOccurredDate()).toString("yyyy-MM-dd HH:mm:ss"));
			transaction.setClearedMerchant(t.getMerchantName());
			transaction.setMerchantLogo(t.getMerchantLogo());
			transaction.setTransactionType(t.getType());
			transaction.setTransactionStatus(t.getStatus());
			transaction.setSpType(SpType.DIVVY.toString());
			if (t.getType().equalsIgnoreCase("decline")) {
				transaction.setProcStatus(PROC_STATUS_WAIT);
			} else {
				transaction.setProcStatus(PROC_STATUS_FINISH);
			}
			if (commonService.getCardinfoByCardId(t.getCardId()) == null) {
				log.error("没有相关卡与id{}对应", t.getCardId());
			} else {
				transaction.setCardNo(commonService.getCardinfoByCardId(t.getCardId()).getCardNo());
				transaction.setCardOwner(commonService.getCardinfoByCardId(t.getCardId()).getCardOwner());
			}
			transaction.setClearedMerchant(t.getClearMerchantName());
			transaction.setDeclineReason(t.getDeclineReason());
			try {
				transactionService.save(transaction);
			} catch (DuplicateKeyException e) {
				log.error("库里已经存在这条交易流水{}了", transaction.getSpTransactionId());
			}

		});
	}

	// @Scheduled(cron = "* 20 * * * *")
	public void GetAllCards() throws Exception {
		val query = new TblCardInfo();
		query.setOrderBy("nickname desc");
		query.setPageSize(1);
		val lastList = cardInfoService.findPage(query);
		val count = cardInfoService.findCount(new TblCardInfo());
		val rsp = getCompanyVirtualCards.init("10", "0").execute();
//        if (rsp.getCardTotalCount() != count) {
//            for (int i = 0; i < rsp.getList().size(); i++) {
//                rsp.getList().get(i).getName()==
//            }
//            );
//        }
	}

	/**
	 * 卡信息缓存
	 */
	@Scheduled(fixedDelay = 60 * 1000) // 30分钟
	public void updateCardCache() {
		commonService.updateCardCache();
	}

	@Scheduled(fixedDelay = 60 * 1000) // 1分钟
	public void updateSiteConfigCache() {
		val param = new TblBizParam();
		param.setKey("SiteSwitch");
		val list = SpringUtils.getBean(TblBizParamService.class).findList(param);
		if (list != null && list.size() > 0) {
			val value = list.get(0).getValue();
			CacheUtils.put(SITE_SWITCH_CACHE, SITE_SWITCH_CACHE_KEY, value);
		}
	}

}
