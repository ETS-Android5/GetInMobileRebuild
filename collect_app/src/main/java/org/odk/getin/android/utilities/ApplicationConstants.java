/*
 * Copyright 2017 Nafundi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.odk.getin.android.utilities;

import org.odk.getin.android.R;

import java.util.HashMap;
import java.util.Map;

public class ApplicationConstants {

    // based on http://www.sqlite.org/limits.html
    public static final int SQLITE_MAX_VARIABLE_NUMBER = 999;

    static final String[] TRANSLATIONS_AVAILABLE = {"af", "am", "ar", "bn", "ca", "cs", "de", "en",
            "es", "et", "fa", "fi", "fr", "hi", "in", "it", "ja", "ka", "km", "ln", "lo_LA", "lt",
            "mg", "ml", "mr", "ms", "my", "ne_NP", "nl", "no", "pl", "ps", "pt", "ro", "ru", "si",
            "sl", "so", "sq", "sr", "sv_SE", "sw", "sw_KE", "te", "th_TH", "ti", "tl", "tr", "uk",
            "ur", "ur_PK", "vi", "zh", "zu"};

    private ApplicationConstants() {

    }

    public static HashMap<Integer, Integer> getSortLabelToIconMap() {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        hashMap.put(R.string.sort_by_name_asc, R.drawable.ic_sort_by_alpha);
        hashMap.put(R.string.sort_by_name_desc, R.drawable.ic_sort_by_alpha);
        hashMap.put(R.string.sort_by_date_asc, R.drawable.ic_access_time);
        hashMap.put(R.string.sort_by_date_desc, R.drawable.ic_access_time);
        hashMap.put(R.string.sort_by_status_asc, R.drawable.ic_assignment_turned_in);
        hashMap.put(R.string.sort_by_status_desc, R.drawable.ic_assignment_late);
        return hashMap;
    }

    public abstract static class BundleKeys {
        public static final String FORM_MODE = "formMode";
        public static final String SUCCESS_KEY = "SUCCESSFUL";
        public static final String ERROR_REASON = "ERROR_MSG";
        public static final String FORM_IDS = "FORM_IDS";
        public static final String MESSAGE = "MESSAGE";
        public static final String USERNAME = "USERNAME";
        public static final String PASSWORD = "PASSWORD";
        public static final String URL = "URL";
        public static final String DELETE_INSTANCE_AFTER_SUBMISSION = "DELETE_INSTANCE_AFTER_SUBMISSION";
    }

    public abstract static class FormModes {
        public static final String EDIT_SAVED = "editSaved";
        public static final String VIEW_SENT = "viewSent";
    }

    public abstract static class SortingOrder {
        public static final int BY_NAME_ASC = 0;
        public static final int BY_NAME_DESC = 1;
        public static final int BY_DATE_DESC = 2;
        public static final int BY_DATE_ASC = 3;
        public static final int BY_STATUS_ASC = 4;
        public static final int BY_STATUS_DESC = 5;
    }

    public abstract static class RequestCodes {
        public static final int IMAGE_CAPTURE = 1;
        // public static final int BARCODE_CAPTURE = 2;
        public static final int AUDIO_CAPTURE = 3;
        public static final int VIDEO_CAPTURE = 4;
        public static final int LOCATION_CAPTURE = 5;
        public static final int HIERARCHY_ACTIVITY = 6;
        public static final int IMAGE_CHOOSER = 7;
        public static final int AUDIO_CHOOSER = 8;
        public static final int VIDEO_CHOOSER = 9;
        public static final int EX_STRING_CAPTURE = 10;
        public static final int EX_INT_CAPTURE = 11;
        public static final int EX_DECIMAL_CAPTURE = 12;
        public static final int DRAW_IMAGE = 13;
        public static final int SIGNATURE_CAPTURE = 14;
        public static final int ANNOTATE_IMAGE = 15;
        public static final int ALIGNED_IMAGE = 16;
        public static final int BEARING_CAPTURE = 17;
        public static final int EX_GROUP_CAPTURE = 18;
        public static final int OSM_CAPTURE = 19;
        public static final int GEOSHAPE_CAPTURE = 20;
        public static final int GEOTRACE_CAPTURE = 21;
        public static final int ARBITRARY_FILE_CHOOSER = 22;

        public static final int FORMS_UPLOADED_NOTIFICATION = 97;
        public static final int FORMS_DOWNLOADED_NOTIFICATION = 98;
        public static final int FORM_UPDATES_AVAILABLE_NOTIFICATION = 99;
    }

    public abstract static class Namespaces {
        static final String XML_OPENROSA_NAMESPACE = "http://openrosa.org/xforms";
        public static final String XML_OPENDATAKIT_NAMESPACE = "http://www.opendatakit.org/xforms";
    }

    public static final String SERVER_TOKEN = "server_token";
    public static final String USER_FIRST_NAME = "user_first_name";
    public static final String USER_LAST_NAME = "user_last_name";
    public static final String USER_DISTRICT = "user_district";
    public static final String USER_NAME = "user_name";
    public static final String VHT_MIDWIFE_ID = "vht_midwife_Id";
    public static final String VHT_MIDWIFE_NAME = "vht_midwife_name";
    public static final String VHT_MIDWIFE_PHONE = "vht_midwife_phone";
    public static final String USER_ROLE = "user_role";
    public static final String USER_LOGGED_IN = "user_logged_in";
    public static final String CHEW_ROLE = "chew";
    public static final String MIDWIFE_ROLE = "midwife";
    public static final String APPOINTMENT_FORM_ID = "GetINAppointment6_chew";
    public static final String APPOINTMENT_FORM_MIDWIFE_ID = "GetINAppointment10_midwife";
    public static final String FOLLOW_UP_FORM_ID = "GetInFollowup20_chew";
    public static final String FOLLOW_UP_FORM_MIDWIFE_ID = "GetInFollowup19_midwife";
    public static final String POSTNATAL_FORM_ID = "GetINPostnatalForm6_chew";
    public static final String POSTNATAL_FORM_MIDWIFE_ID = "GetINPostnatalForm6_midwife";
    public static final String GIRL_ID = "GIRL_ID";
    public static final String GIRL_NAME = "GIRL_NAME";
    public static final String GIRL_VOUCHER_NUMBER = "GIRL_VOUCHER_NUMBER";
    public static final String GIRL_REDEEMED_SERVICES = "GIRL_REDEEMED_SERVICES";
    public static final String GIRL_REDEEM_SERVICE_SELECTED = "GIRL_REDEEMED_SERVICE_SELECTED";
    public static final String GIRL_FIRST_NAME = "GIRL_FIRST_NAME";
    public static final String GIRL_LAST_NAME = "GIRL_LAST_NAME";
    public static final String EDIT_GIRL = "EDIT_GIRL";
    public static final String USER_ID = "USER_ID";
    public static final String LAST_NOTIFICATION_TIME = "last_notification";
    public static final String SHOW_NETWORK_STATUS = "show_network_status";
    public static final String HEALTH_FACILITY = "health_facility";

    public static String getChewUserMappingForm(String district) {
        Map<String, String> forms = new HashMap<>();
        forms.put("ABIM", "GetInMapGirlABIM1_chew");
        forms.put("ADJUMANI", "GetInMapGirlADJUMANI1_chew");
        forms.put("AGAGO", "GetInMapGirlAGAGO1_chew");
        forms.put("ALEBTONG", "GetInMapGirlALEBTONG1_chew");
        forms.put("AMOLATAR", "GetInMapGirlAMOLATAR1_chew");
        forms.put("AMUDAT", "GetInMapGirlAMUDAT1_chew");
        forms.put("AMURIA", "GetInMapGirlAMURIA1_chew");
        forms.put("AMURU", "GetInMapGirlAMURU1_chew");
        forms.put("APAC", "GetInMapGirlAPAC1_chew");
        forms.put("ARUA", "GetInMapGirlArua3_chew");
        forms.put("BUDAKA", "GetInMapGirlBUDAKA1_chew");
        forms.put("BUDUDA", "GetInMapGirlBUDUDA1_chew");
        forms.put("BUGIRI", "GetInMapGirlBUGIRI1_chew");
        forms.put("BUHWEJU", "GetInMapGirlBUHWEJU1_chew");
        forms.put("BUIKWE", "GetInMapGirlBUIKWE1_chew");
        forms.put("BUKEDEA", "GetInMapGirlBUKEDEA1_chew");
        forms.put("BUKOMANSIMBI", "GetInMapGirlBUKOMANSIMBI1_chew");
        forms.put("BUKWO", "GetInMapGirlBUKWO1_chew");
        forms.put("BULAMBULI", "GetInMapGirlBULAMBULI1_chew");
        forms.put("BULIISA", "GetInMapGirlBULIISA1_chew");
        forms.put("BUNDIBUGYO", "GetInMapGirlBundibugyo17_chew");
        forms.put("BUSHENYI", "GetInMapGirlBUSHENYI1_chew");
        forms.put("BUSIA", "GetInMapGirlBUSIA1_chew");
        forms.put("BUTALEJA", "GetInMapGirlBUTALEJA1_chew");
        forms.put("BUTAMBALA", "GetInMapGirlBUTAMBALA1_chew");
        forms.put("BUVUMA", "GetInMapGirlBUVUMA1_chew");
        forms.put("BUYENDE", "GetInMapGirlBUYENDE1_chew");
        forms.put("DOKOLO", "GetInMapGirlDOKOLO1_chew");
        forms.put("GOMBA", "GetInMapGirlGOMBA1_chew");
        forms.put("GULU", "GetInMapGirlGULU1_chew");
        forms.put("HOIMA", "GetInMapGirlHOIMA1_chew");
        forms.put("IBANDA", "GetInMapGirlIBANDA1_chew");
        forms.put("IGANGA", "GetInMapGirlIGANGA1_chew");
        forms.put("ISINGIRO", "GetInMapGirlISINGIRO1_chew");
        forms.put("JINJA", "GetInMapGirlJINJA1_chew");
        forms.put("KAABONG", "GetInMapGirlKAABONG1_chew");
        forms.put("KABALE", "GetInMapGirlKABALE1_chew");
        forms.put("KABAROLE", "GetInMapGirlKABAROLE1_chew");
        forms.put("KABERAMAIDO", "GetInMapGirlKABERAMAIDO1_chew");
        forms.put("KAGADI", "GetInMapGirlKAGADI1_chew");
        forms.put("KAKUMIRO", "GetInMapGirlKAKUMIRO1_chew");
        forms.put("KALANGALA", "GetInMapGirlKALANGALA1_chew");
        forms.put("KALIRO", "GetInMapGirlKALIRO1_chew");
        forms.put("KALUNGU", "GetInMapGirlKALUNGU1_chew");
        forms.put("KAMPALA", "GetInMapGirlKampala1_chew");
        forms.put("KAMULI", "GetInMapGirlKAMULI1_chew");
        forms.put("KAMWENGE", "GetInMapGirlKAMWENGE1_chew");
        forms.put("KANUNGU", "GetInMapGirlKANUNGU1_chew");
        forms.put("KAPCHORWA", "GetInMapGirlKAPCHORWA1_chew");
        forms.put("KASESE", "GetInMapGirlKASESE1_chew");
        forms.put("KATAKWI", "GetInMapGirlKATAKWI1_chew");
        forms.put("KAYUNGA", "GetInMapGirlKAYUNGA1_chew");
        forms.put("KIBAALE", "GetInMapGirlKIBAALE1_chew");
        forms.put("KIBOGA", "GetInMapGirlKIBOGA1_chew");
        forms.put("KIBUKU", "GetInMapGirlKIBUKU1_chew");
        forms.put("KIRUHURA", "GetInMapGirlKIRUHURA1_chew");
        forms.put("KIRYANDONGO", "GetInMapGirlKIRYANDONGO1_chew");
        forms.put("KISORO", "GetInMapGirlKISORO1_chew");
        forms.put("KITGUM", "GetInMapGirlKITGUM1_chew");
        forms.put("KOBOKO", "GetInMapGirlKOBOKO1_chew");
        forms.put("KOLE", "GetInMapGirlKOLE1_chew");
        forms.put("KOTIDO", "GetInMapGirlKOTIDO1_chew");
        forms.put("KUMI", "GetInMapGirlKUMI1_chew");
        forms.put("KWEEN", "GetInMapGirlKWEEN1_chew");
        forms.put("KYANKWANZI", "GetInMapGirlKYANKWANZI1_chew");
        forms.put("KYEGEGWA", "GetInMapGirlKYEGEGWA1_chew");
        forms.put("KYENJOJO", "GetInMapGirlKYENJOJO1_chew");
        forms.put("LAMWO", "GetInMapGirlLAMWO1_chew");
        forms.put("LIRA", "GetInMapGirlLIRA1_chew");
        forms.put("LUUKA", "GetInMapGirlLUUKA1_chew");
        forms.put("LUWERO", "GetInMapGirlLUWERO1_chew");
        forms.put("LWENGO", "GetInMapGirlLWENGO1_chew");
        forms.put("LYANTONDE", "GetInMapGirlLYANTONDE1_chew");
        forms.put("MANAFWA", "GetInMapGirlMANAFWA1_chew");
        forms.put("MARACHA", "GetInMapGirlMARACHA1_chew");
        forms.put("MASAKA", "GetInMapGirlMASAKA1_chew");
        forms.put("MASINDI", "GetInMapGirlMASINDI1_chew");
        forms.put("MAYUGE", "GetInMapGirlMAYUGE1_chew");
        forms.put("MBALE", "GetInMapGirlMBALE1_chew");
        forms.put("MBARARA", "GetInMapGirlMBARARA1_chew");
        forms.put("MITOOMA", "GetInMapGirlMITOOMA1_chew");
        forms.put("MITYANA", "GetInMapGirlMITYANA1_chew");
        forms.put("MOROTO", "GetInMapGirlMOROTO1_chew");
        forms.put("MOYO", "GetInMapGirlMOYO1_chew");
        forms.put("MPIGI", "GetInMapGirlMPIGI1_chew");
        forms.put("MUBENDE", "GetInMapGirlMUBENDE1_chew");
        forms.put("MUKONO", "GetInMapGirlMUKONO1_chew");
        forms.put("NAKAPIRIPIRIT", "GetInMapGirlNAKAPIRIPIRIT1_chew");
        forms.put("NAKASEKE", "GetInMapGirlNAKASEKE1_chew");
        forms.put("NAKASONGOLA", "GetInMapGirlNAKASONGOLA1_chew");
        forms.put("NAMAYINGO", "GetInMapGirlNAMAYINGO1_chew");
        forms.put("NAMUTUMBA", "GetInMapGirlNAMUTUMBA1_chew");
        forms.put("NAPAK", "GetInMapGirlNAPAK1_chew");
        forms.put("NEBBI", "GetInMapGirlNEBBI1_chew");
        forms.put("NGORA", "GetInMapGirlNGORA1_chew");
        forms.put("NTOROKO", "GetInMapGirlNTOROKO1_chew");
        forms.put("NTUNGAMO", "GetInMapGirlNTUNGAMO1_chew");
        forms.put("NWOYA", "GetInMapGirlNWOYA1_chew");
        forms.put("OTUKE", "GetInMapGirlOTUKE1_chew");
        forms.put("OYAM", "GetInMapGirlOYAM1_chew");
        forms.put("PADER", "GetInMapGirlPADER1_chew");
        forms.put("PALLISA", "GetInMapGirlPALLISA1_chew");
        forms.put("RAKAI", "GetInMapGirlRAKAI1_chew");
        forms.put("RUBIRIZI", "GetInMapGirlRUBIRIZI1_chew");
        forms.put("RUKUNGIRI", "GetInMapGirlRUKUNGIRI1_chew");
        forms.put("SERERE", "GetInMapGirlSERERE1_chew");
        forms.put("SHEEMA", "GetInMapGirlSHEEMA1_chew");
        forms.put("SIRONKO", "GetInMapGirlSIRONKO1_chew");
        forms.put("SOROTI", "GetInMapGirlSOROTI1_chew");
        forms.put("SSEMBABULE", "GetInMapGirlSSEMBABULE1_chew");
        forms.put("TORORO", "GetInMapGirlTORORO1_chew");
        forms.put("WAKISO", "GetInMapGirlWAKISO1_chew");
        forms.put("YUMBE", "GetInMapGirlYumbe1_chew");
        forms.put("ZOMBO", "GetInMapGirlZOMBO1_chew");
        return forms.get(district);
    }

    public static String getMidwifeUserMappingForm(String district) {
        Map<String, String> forms = new HashMap<>();
        forms.put("BUNDIBUGYO", "GetInMapGirlBundibugyo16_midwife");
        forms.put("ARUA", "GetInMapGirlArua3_midwife");
        forms.put("KAMPALA", "GetInMapGirlKampala1_midwife");
        forms.put("MOYO", "GetInMapGirlMoyo1_midwife");
        forms.put("ADJUMANI", "GetInMapGirlAdjumani1_midwife");
        forms.put("YUMBE", "GetInMapGirlYumbe1_midwife");
        return forms.get(district);
    }
}
