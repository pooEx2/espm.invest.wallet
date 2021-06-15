package br.espm.invest.wallet;

import br.espm.invest.wallet.common.datatype.Wallet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wallet")
public class WalletModel {

    @Id
    @Column(name = "id_wallet")
    private String id;

    @Column(name = "id_user")
    private String idUser;

    @Column(name = "vl_balance")
    private double balance;

    public WalletModel(){}

    public WalletModel(Wallet w) {
        this.id = w.getId();
        // this.idUser = w.getUser();
        this.balance = w.getBalance();
    }

    public Wallet to() {
        // User user = new User();
        // user.setId(idUser)

        Wallet w = new Wallet();
        w.setId(this.id);
        w.setBalance(this.balance);
        // w.setUser(this.idUser)
        return w;
    }
}
