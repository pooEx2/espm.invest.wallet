package br.espm.invest.wallet;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WalletRepository extends CrudRepository<WalletModel, String> {

    @Override
    Iterable<WalletModel> findAll();

    @Override
    Optional<WalletModel> findById(String s);

    @Override
    WalletModel save(WalletModel w);

//    @Query("SELECT q from QuotationModel q WHERE q.idCurrency = :idCurrency AND q.dtDate <= :date ORDER BY q.dtDate DESC")
//    List<QuotationModel> listByCurrencyDate(
//            @Param("idCurrency") String idCurrency,
//            @Param("date") Date date);
//
//    @Query("SELECT q FROM QuotationModel q " +
//            "WHERE " +
//            "(q.idCurrency is null or q.idCurrency = :idCurrency) AND " +
//            "(q.dtDate is null or q.dtDate >= :dtStart) AND " +
//            "(q.dtDate is null or q.dtDate <= :dtEnd)"
//    )
//    List<QuotationModel> listBy(
//            @Param("idCurrency") String idCurrency,
//            @Param("dtStart") Date dtStart,
//            @Param("dtEnd") Date dtEnd
//    );
}
