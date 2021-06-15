package br.espm.invest.wallet;

import br.espm.invest.exchange.common.datatype.Quotation;
import br.espm.invest.wallet.common.datatype.ExchangeTransaction;
import br.espm.invest.wallet.common.datatype.TransactionType;
import br.espm.invest.wallet.common.datatype.Wallet;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "exchangetransaction")
public class ExchangeTransactionModel {

    @Id
    @Column(name = "id_exchangetransaction")
    private String idTransaction;

    @Column(name = "id_wallet")
    private String idWallet;

    @Column(name = "id_quotation")
    private String idQuotation;

    @Column(name = "dt_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtDate;

    @Column(name = "nr_amount")
    private double nrAmount;

    @Column(name = "nr_type")
    private Integer nrType;

    public ExchangeTransactionModel() {}

    public ExchangeTransactionModel(ExchangeTransaction et) {
        this.idTransaction = et.getId();
        this.dtDate = et.getDate();
        this.idWallet = et.getWallet().getId();
        this.idQuotation = et.getQuotation().getId();
        this.nrType = et.getType().equals(TransactionType.SELL) ? 1:2;
        this.nrAmount = et.getAmount();
    }

    public ExchangeTransaction to() {
        Wallet wallet = new Wallet();
        wallet.setId(idWallet);

        Quotation quotation = new Quotation();
        quotation.setId(idQuotation);

        ExchangeTransaction et = new ExchangeTransaction();
        et.setId(idTransaction);
        et.setDate(dtDate);
        et.setAmount(nrAmount);
        et.setType(1 == nrType ? TransactionType.SELL : TransactionType.BUY);
        et.setWallet(wallet);
        et.setQuotation(quotation);
        return et;
    }
}
