package br.espm.invest.wallet;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StocksTransactionRepository extends CrudRepository<StocksTransactionModel, String> {

    @Override
    StocksTransactionModel save(StocksTransactionModel s);

    @Override
    Optional<StocksTransactionModel> findById(String id);

    @Query("SELECT et from StocksTransactionModel et where et.idWallet = :idWallet order by et.dtDate")
    List<ExchangeTransactionModel> listByWallet(@Param("idWallet") String idWallet);
}
