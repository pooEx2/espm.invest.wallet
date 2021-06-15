package br.espm.invest.wallet;

import br.espm.invest.exchange.common.controller.ExchangeController;
import br.espm.invest.wallet.common.datatype.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class WalletService {

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private ExchangeController exchangeController;

    @Autowired
    private ExchangeTransactionService exchangeTransactionService;

    public Wallet create(Wallet w) {
        w.setId(UUID.randomUUID().toString());
        return walletRepository.save(new WalletModel()).to();
    }

    public Wallet findBy(String id) {
        Wallet w = walletRepository
                .findById(id)
                .map(WalletModel::to)
                .orElse(null);
        if (w != null) {
            // Aqui e uma amarracao mapping 1 .. n
            w.setExchangeTransactions(exchangeTransactionService.listByWallet(w.getId()));
            w.getExchangeTransactions().forEach(exchangeTransaction -> {
                exchangeTransaction.setQuotation(exchangeController.quotation(exchangeTransaction.getQuotation().getId()));
            });
        }
        return w;
    }
}
