package br.espm.invest.wallet;

import br.espm.invest.exchange.common.datatype.Quotation;
import br.espm.invest.wallet.common.datatype.StocksTransaction;
import br.espm.invest.wallet.common.datatype.TransactionType;
import br.espm.invest.wallet.common.datatype.Wallet;
import br.espm.poo1.stocks.common.datatype.Price;
import br.espm.poo1.stocks.common.datatype.Stocks;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stockstransaction")
public class StocksTransactionModel {

    @Id
    @Column(name = "id_stockstransaction")
    private String idTransaction;

    @Column(name = "id_wallet")
    private String idWallet;

    @Column(name = "id_stocks")
    private String idStocks;

    @Column(name = "id_price")
    private String idPrice;

    @Column(name = "dt_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtDate;

    @Column(name = "nr_amount")
    private double nrAmount;

    @Column(name = "nr_type")
    private Integer nrType;

    public StocksTransactionModel(StocksTransaction et) {
        this.idTransaction = et.getId();
        this.dtDate = et.getDate();
        this.idWallet = et.getWallet().getId();
        this.idStocks = et.getStocks().getId();
        this.idPrice = et.getPrice().getId();
        this.nrType = et.getType().equals(TransactionType.SELL) ? 1:2;
        this.nrAmount = et.getAmount();
    }

    public StocksTransaction to() {
        Wallet wallet = new Wallet();
        wallet.setId(idWallet);

        Stocks stocks = new Stocks();
        stocks.setId(idStocks);

        Price price = new Price();
        price.setId(idPrice);

        StocksTransaction et = new StocksTransaction();
        et.setId(idTransaction);
        et.setDate(dtDate);
        et.setAmount(nrAmount);
        et.setType(1 == nrType ? TransactionType.SELL : TransactionType.BUY);
        et.setWallet(wallet);
        et.setStocks(stocks);
        et.setPrice(price);
        return et;
    }
}
