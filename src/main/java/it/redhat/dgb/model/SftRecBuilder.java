package it.redhat.dgb.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SftRecBuilder {
    static List<String> fileNames = new ArrayList<>(Arrays.asList("TRDTI_SFTREC_CACON_R0047E-221117_004003-0.xml", "TRRGS_SFTREC_CACON_R0047E-221116_002001-0.xml", "TRRGS_SFTREC_CACON_R0047E-221116_002001-0.xml"));
    static List<String> counterParties = new ArrayList<>(Arrays.asList("815600E4E6DCD2D25E30", "8156004F298245FBB836", "549300TRUWO2CD2G5692", "8156004F298245FBB836", "8156004F298245FBB836", "8156004F298245FBB836"));
    static List<String> utis = new ArrayList<>(Arrays.asList("LCHCBF2210288850247BTAM20221028S", "E028156004F298245FBB836CAD01SLGG20221024220659009452", "CADPDTTERZI22545430", "E028156004F298245FBB836CAD01SLGG20221019220646004932", "E028156004F298245FBB836CAD01SLGG20221024220659005259", "E028156004F298245FBB836CAD01SLGG20221026221037009025"));
    static List<String> rids = new ArrayList<>(Arrays.asList("0038042588000E028156004F298245FBB836CAD01SLGG202210262210370090258156004F298245FBB8362", "EUSFNANANASFTB56519251"));
    static List<String> treps = new ArrayList<>(Arrays.asList("TRRGS", "TRDTI"));
    static List<String> rstats = new ArrayList<>(Arrays.asList("CLRC", "LNRC", "PARD", "RECO", "UNPR"));
    
    public static SftRec build(BenchmarkLoaderConfiguration data, int day){
        long baseDate = data.getStartDay() + (day * 24 * 60 * 60 * 1000);
        Random random = new Random();
        String filename = fileNames.get(random.nextInt(fileNames.size()));
        String[] BMI_split = filename.split("_");
        String bmi = BMI_split[3];

        return new SftRec(
            // data_di_caricamento -- equivale al momento in cui la entry viene processata
            java.lang.System.currentTimeMillis(),
            // id_progressivo
            random.nextLong(),
            // nome_file -- preso randomicamente da una lista di nomi
            filename,
            // "Business_Message_Identifier": extracted from file name, kind of "R0047E-221117"
            bmi,
            "auth.080.001.02",           // 5
            "SFTR_PROD",
            // "Creation_Date": 1668676333000
            (baseDate + 36733),
            // "Reporting_counterparty": "8156004F298245FBB836"
            counterParties.get(random.nextInt(counterParties.size())),
            // UTI
            utis.get(random.nextInt(utis.size())),
            // Other_counterparty: is a string
            "R1IO4YJ0O79SMWVCHB58",        // 10
            // Master_agreement_type: string always "OTHR"
            "OTHR",
            // Report_status
            //"CLRC | LNRC | PARD | RECO | UNPR",
            rstats.get(random.nextInt(rstats.size())),
            // Reporting_timestamp: NOT FOUND in the the examples, using basedate
            baseDate,
            // "Modification_status": a string that can be "true" or "false"
            String.valueOf(random.nextBoolean()),
            // No_Reconciliati_on_required: not found in the examples
            "false",                    // 15
            // Matching_status: only one example with "Not Matched"
            "Not Matched",
            // Loan_reconciliatio_n_status
            "Reconciled",
            // Reportable_loan_fields_subject_of_reconciliation: no examples
            "none",
            //  Collateral_reconciliation_status: string
            "Reconciled",
            // Reportable_collateral_fields_subject_of_reconciliation: string no examples
            "none",                    // 20
            // Technical_Record_Identification: string from a list
            rids.get(random.nextInt(rids.size())),
            baseDate,                 // 20
            treps.get(random.nextInt(treps.size())),
            "SFTREC",
            baseDate
        );
    }
}