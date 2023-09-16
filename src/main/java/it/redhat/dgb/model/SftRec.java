package it.redhat.dgb.model;

import java.io.Serializable;

import org.infinispan.api.annotations.indexing.Indexed;
import org.infinispan.protostream.annotations.ProtoDoc;
import org.infinispan.protostream.annotations.ProtoFactory;
import org.infinispan.protostream.annotations.ProtoField;

@Indexed
public class SftRec implements Serializable {

    private long data_di_caricamento;
    private long id_progressivo;
    private String nome_file;
    private String Business_Message_Identifier;
    private String Message_Definition_Identifier;  // 5
    private String Business_Service;
    private long Creation_Date;
    private String Reporting_counterparty;
    private String UTI;
    private String Other_counterparty;  // 10
    private String Master_agreement_type;
    private String Report_status;
    private long Reporting_timestamp;
    private String Modification_status;
    private String No_Reconciliati_on_required;  // 15
    private String Matching_status;
    private String Loan_reconciliatio_n_status;
    private String Reportable_loan_fields_subject_of_reconciliation;
    private String Collateral_reconciliation_status;
    private String Reportable_collateral_fields_subject_of_reconciliation;  // 20
    private String Technical_Record_Identification;
    private long Data_di_ricezione;
    private String Trade_repository;
    private String Flusso;
    private long Received_Report_Date;

    @ProtoFactory
    public SftRec(long data_di_caricamento, long id_progressivo, String nome_file, String business_Message_Identifier,
            String message_Definition_Identifier, String business_Service, long creation_Date,
            String reporting_counterparty, String uTI, String other_counterparty, String master_agreement_type,
            String report_status, long reporting_timestamp, String modification_status,
            String no_Reconciliati_on_required, String matching_status, String loan_reconciliatio_n_status,
            String reportable_loan_fields_subject_of_reconciliation, String collateral_reconciliation_status,
            String reportable_collateral_fields_subject_of_reconciliation, String technical_Record_Identification,
            long data_di_ricezione, String trade_repository, String flusso, long received_Report_Date) {
        this.data_di_caricamento = data_di_caricamento;
        this.id_progressivo = id_progressivo;
        this.nome_file = nome_file;
        Business_Message_Identifier = business_Message_Identifier;
        Message_Definition_Identifier = message_Definition_Identifier;
        Business_Service = business_Service;
        Creation_Date = creation_Date;
        Reporting_counterparty = reporting_counterparty;
        UTI = uTI;
        Other_counterparty = other_counterparty;
        Master_agreement_type = master_agreement_type;
        Report_status = report_status;
        Reporting_timestamp = reporting_timestamp;
        Modification_status = modification_status;
        No_Reconciliati_on_required = no_Reconciliati_on_required;
        Matching_status = matching_status;
        Loan_reconciliatio_n_status = loan_reconciliatio_n_status;
        Reportable_loan_fields_subject_of_reconciliation = reportable_loan_fields_subject_of_reconciliation;
        Collateral_reconciliation_status = collateral_reconciliation_status;
        Reportable_collateral_fields_subject_of_reconciliation = reportable_collateral_fields_subject_of_reconciliation;
        Technical_Record_Identification = technical_Record_Identification;
        Data_di_ricezione = data_di_ricezione;
        Trade_repository = trade_repository;
        Flusso = flusso;
        Received_Report_Date = received_Report_Date;
    }

    @ProtoField(required = false, number = 1)
    public long getData_di_caricamento() {
        return data_di_caricamento;
    }

    @ProtoField(required = true, number = 2)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public long getId_progressivo() {
        return id_progressivo;
    }

    @ProtoField(required = true, number = 3)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public String getNome_file() {
        return nome_file;
    }
    @ProtoField(required = false, number = 4)
    public String getBusiness_Message_Identifier() {
        return Business_Message_Identifier;
    }
    @ProtoField(required = false, number = 5)
    public String getMessage_Definition_Identifier() {
        return Message_Definition_Identifier;
    }
    @ProtoField(required = false, number = 6)
    public String getBusiness_Service() {
        return Business_Service;
    }
    @ProtoField(required = false, number = 7)
    public long getCreation_Date() {
        return Creation_Date;
    }
    @ProtoField(required = true, number = 8)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public String getReporting_counterparty() {
        return Reporting_counterparty;
    }
    @ProtoField(required = false, number = 9)
    public String getUTI() {
        return UTI;
    }
    @ProtoField(required = true, number = 10)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public String getOther_counterparty() {
        return Other_counterparty;
    }
    @ProtoField(required = false, number = 11)
    public String getMaster_agreement_type() {
        return Master_agreement_type;
    }
    @ProtoField(required = false, number = 12)
    public String getReport_status() {
        return Report_status;
    }
    @ProtoField(required = true, number = 13)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public long getReporting_timestamp() {
        return Reporting_timestamp;
    }
    @ProtoField(required = false, number = 14)
    public String getModification_status() {
        return Modification_status;
    }
    @ProtoField(required = false, number = 15)
    public String getNo_Reconciliati_on_required() {
        return No_Reconciliati_on_required;
    }
    @ProtoField(required = true, number = 16)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public String getMatching_status() {
        return Matching_status;
    }
    @ProtoField(required = true, number = 17)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public String getLoan_reconciliatio_n_status() {
        return Loan_reconciliatio_n_status;
    }
    @ProtoField(required = false, number = 18)
    public String getReportable_loan_fields_subject_of_reconciliation() {
        return Reportable_loan_fields_subject_of_reconciliation;
    }
    @ProtoField(required = true, number = 19)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public String getCollateral_reconciliation_status() {
        return Collateral_reconciliation_status;
    }
    @ProtoField(required = false, number = 20)
    public String getReportable_collateral_fields_subject_of_reconciliation() {
        return Reportable_collateral_fields_subject_of_reconciliation;
    }
    @ProtoField(required = false, number = 21)
    public String getTechnical_Record_Identification() {
        return Technical_Record_Identification;
    }
    @ProtoField(required = false, number = 22)
    public long getData_di_ricezione() {
        return Data_di_ricezione;
    }
    @ProtoField(required = false, number = 23)
    public String getTrade_repository() {
        return Trade_repository;
    }
    @ProtoField(required = false, number = 24)
    public String getFlusso() {
        return Flusso;
    }
    @ProtoField(required = true, number = 25)
    @ProtoDoc("@Field(index = Index.YES, store = Store.NO, analyze = Analyze.NO)")
    public long getReceived_Report_Date() {
        return Received_Report_Date;
    }
   
}