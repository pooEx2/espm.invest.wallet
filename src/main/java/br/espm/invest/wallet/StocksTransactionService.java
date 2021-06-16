package br.espm.invest.wallet;

import br.espm.poo1.stocks.common.controller.StocksController;
import br.espm.poo1.stocks.common.datatype.Price;
import br.espm.poo1.stocks.common.datatype.Stocks;
import org.springframework.beans.factory.annotation.Autowired;
import br.espm.invest.wallet.common.datatype.StocksTransaction;
import br.espm.invest.wallet.common.datatype.TransactionBean;
import br.espm.invest.wallet.common.datatype.TransactionType;
import br.espm.invest.wallet.common.datatype.Wallet;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class StocksTransactionService {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    private StocksController stocksController;

    @Autowired
    private WalletService walletService;

    public List<StocksTransaction> listByWallet(String idWallet) {
        List<StocksTransaction> l = stocksTransactionRepository
                .listByWallet(idWallet).stream()
                .map(StocksTransactionModel::to)
                .collect(Collectors.toList());
        return l;
    }

    public StocksTransaction buy(String idWallet, TransactionBean bean) {
        Wallet w = walletService.findBy(idWallet);
        if (w == null) {
            throw new RuntimeException("Wallet does not exist: " + idWallet);
        }

        Date now = new Date();

        Stocks stocks = stocksController.stocks(bean.getSymbol());
        if (stocks == null) {
            throw new RuntimeException("Stock does not exist: " + bean.getSymbol());
        }

        Price price = stocksController.price(bean.getId());
        if (price == null) {
            throw new RuntimeException("Price does not exist");
        }

        StocksTransaction et = new StocksTransaction();
        et.setId(UUID.randomUUID().toString());
        et.setWallet(w);
        et.setStocks(stocks);
        et.setType(TransactionType.BUY);
        et.setAmount(bean.getAmount());
        et.setDate(now);

        return stocksTransactionRepository.save(new StocksTransactionModel(et)).to();
    }

        public StocksTransaction sell(String idWallet, TransactionBean bean) {
            Wallet w = walletService.findBy(idWallet);
            if (w == null) {
                throw new RuntimeException("Wallet does not exist: " + idWallet);
            }

            Date now = new Date();

            Stocks stocks = stocksController.stocks(bean.getSymbol());
            if (stocks == null) {
                throw new RuntimeException("Stock does not exist: " + bean.getSymbol());
            }

            Price price = stocksController.price(bean.getId());
            if (price == null) {
                throw new RuntimeException("Price does not exist");
            }

            if(bean.getLimit() > 0 && bean.getLimit() - price.getValue() > 0) {
                throw new RuntimeException("Quotation limit, current: " + price.getValue());
            }

            StocksTransaction et = new StocksTransaction();
            et.setId(UUID.randomUUID().toString());
            et.setWallet(w);
            et.setStocks(stocks);
            et.setType(TransactionType.BUY);
            et.setAmount(bean.getAmount());
            et.setDate(now);

            return stocksTransactionRepository.save(new StocksTransactionModel(et)).to();
        }
    }

