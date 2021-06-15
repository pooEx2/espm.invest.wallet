package br.espm.invest.wallet;

import br.espm.invest.wallet.common.controller.WalletController;
import br.espm.invest.wallet.common.datatype.ExchangeTransaction;
import br.espm.invest.wallet.common.datatype.TransactionBean;
import br.espm.invest.wallet.common.datatype.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WalletResource implements WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired // para ligar automaticamente quando estiver no ar
    private ExchangeTransactionService exchangeTransactionService;

    @Override
    public List<Wallet> wallets() {
        return null;
    }

    @Override
    public Wallet wallet(String id) {
        return walletService.findBy(id);
    }

    @Override
    public Wallet create(Wallet wallet) {
        Wallet w = new Wallet();
        w.setBalance(0.0);
        return walletService.create(w);
    }

    @Override
    public ExchangeTransaction exchangeBuy(String idWallet, TransactionBean bean) {
        return null;
    }

    @Override
    public ExchangeTransaction exchangeSell(String idWallet, TransactionBean bean) {
        return null;
    }

    public ExchangeTransaction buyExchange(String idWallet, TransactionBean bean) {
        return exchangeTransactionService.buy(idWallet, bean);
    }

    public ExchangeTransaction sellExchange(String idWallet, TransactionBean bean) {
        return exchangeTransactionService.sell(idWallet, bean);
    }
}
