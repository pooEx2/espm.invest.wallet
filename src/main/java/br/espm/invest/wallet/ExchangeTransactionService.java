package br.espm.invest.wallet;

import br.espm.invest.exchange.common.controller.ExchangeController;
import br.espm.invest.exchange.common.datatype.Currency;
import br.espm.invest.exchange.common.datatype.Quotation;
import br.espm.invest.wallet.common.datatype.ExchangeTransaction;
import br.espm.invest.wallet.common.datatype.TransactionBean;
import br.espm.invest.wallet.common.datatype.TransactionType;
import br.espm.invest.wallet.common.datatype.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ExchangeTransactionService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private ExchangeController exchangeController;

    @Autowired
    private WalletService walletService;

    @Autowired
    private ExchangeTransactionRepository exchangeTransactionRepository;

    public List<ExchangeTransaction> listByWallet(String idWallet) {
        List<ExchangeTransaction> l = exchangeTransactionRepository
                .listByWallet(idWallet).stream()
                .map(ExchangeTransactionModel::to)
                .collect(Collectors.toList());
        return l;
    }

    public ExchangeTransaction buy(String idWallet, TransactionBean bean) {
        Wallet w = walletService.findBy(idWallet);
        if (w == null) {
            throw new RuntimeException("Wallet does not exists: " + idWallet);
        }

        Date now = new Date();

        Currency currency = exchangeController.currency(bean.getSymbol());
        if (currency == null) {
            throw new RuntimeException("Currency does not exists: " + bean.getSymbol());
        }

        Quotation quotation = exchangeController.quotation(currency.getSymbol(), sdf.format(now));
        if (quotation == null) {
            throw new RuntimeException("Quotation does not exists: " + sdf.format(now));
        }

        ExchangeTransaction et = new ExchangeTransaction();
        et.setId(UUID.randomUUID().toString());
        et.setWallet(w);
        et.setQuotation(quotation);
        et.setType(TransactionType.BUY);
        et.setAmount(bean.getAmount());
        et.setDate(now);

        return exchangeTransactionRepository.save(new ExchangeTransactionModel(et)).to();
    }

    public ExchangeTransaction sell(String idWallet, TransactionBean bean) {
        Wallet w = walletService.findBy(idWallet);
        if (w == null) {
            throw new RuntimeException("Wallet does not exists: " + idWallet);
        }

        Date now = new Date();

        Currency currency = exchangeController.currency(bean.getSymbol());
        if (currency == null) {
            throw new RuntimeException("Currency does not exists: " + bean.getSymbol());
        }

        Quotation quotation = exchangeController.quotation(currency.getSymbol(), sdf.format(now));
        if (quotation == null) {
            throw new RuntimeException("Quotation does not exists: " + sdf.format(now));
        }

        if(bean.getLimit() > 0 && bean.getLimit() - quotation.getValue() > 0) {
            throw new RuntimeException("Quotation limit, current: " + quotation.getValue());
        }

            ExchangeTransaction et = new ExchangeTransaction();
            et.setId(UUID.randomUUID().toString());
            et.setWallet(w);
            et.setQuotation(quotation);
            et.setType(TransactionType.SELL);
            et.setAmount(bean.getAmount());
            et.setDate(now);

            return exchangeTransactionRepository.save(new ExchangeTransactionModel(et)).to();
        }
    }
