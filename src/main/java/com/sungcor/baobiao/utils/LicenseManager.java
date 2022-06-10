package com.sungcor.baobiao.utils;

import java.io.File;
import java.util.HashMap;

public class LicenseManager {
    private static String licenseString;
    private static HashMap<String, HashMap<String, LicenseModule>> licenseMap = null;
    private static boolean LICENSE_UPDATE_FLAG = false;
    private static String MODULE_OFF = "000";
    private static String MODULE_ON = "001";
    public static int VAILD_RESULT = 999999;
    public static String AUTHORIZATION_DATE_TERMLESS = "00000000";
    public static String ITMANAGER_PRODUCT_KEY = "ITManager";
    public static String ITSM_PRODUCT_KEY = "ITSM";
    public static String ITOM_PRODUCT_KEY = "ITOM";
    public static String UNIVIEW_PRODUCT_KEY = "Uniview";
    public static String ORMS_PRODUCT_KEY = "ORMS";
    public static String NTAS_PRODUCT_KEY = "NTAS";
    public static String VIRTUAL_PRODUCT_KEY = "Virtual";
    public static String IDC_PRODUCT_KEY = "IDC";
    public static String CESHU_PRODUCT_KEY = "CESHU";
    public static String ITSQM_PRODUCT_KEY = "ITSQM";
    public static String EXTEND1_PRODUCT_KEY = "EXTEND1";
    public static String EXTEND2_PRODUCT_KEY = "EXTEND2";
    public static String EXTEND3_PRODUCT_KEY = "EXTEND3";
    public static String EXTEND4_PRODUCT_KEY = "EXTEND4";

    public LicenseManager() {
    }

    public static int checkLicense(String licPath, String productKey) {
        File licFile = new File(licPath);
        if (!licFile.exists()) {
            System.out.println("Wiserv authorize License Error: code[1]");
            return 1;
        } else {
            loadLicense(licPath);
            if (null != licenseString && !"".equals(licenseString)) {
                if (-1 == licenseString.indexOf(productKey)) {
                    if ("Error1".equals(licenseString)) {
                        return 1;
                    } else if ("Error2".equals(licenseString)) {
                        return 2;
                    } else if ("Error3".equals(licenseString)) {
                        return 3;
                    } else if ("Error4".equals(licenseString)) {
                        return 4;
                    } else {
                        return "Error5".equals(licenseString) ? 5 : -2;
                    }
                } else {
                    if (null == licenseMap || 0 == licenseMap.size() || LICENSE_UPDATE_FLAG) {
                        praseLicense();
                    }

                    return VAILD_RESULT;
                }
            } else {
                return -1;
            }
        }
    }

    public static String getAuthorizationDate() {
        if (null != licenseString && !"".equals(licenseString)) {
            int idx = licenseString.indexOf("#");
            if (-1 == idx) {
                return null;
            } else {
                String authDate = licenseString.substring(0, idx);
                if (null != authDate && !"".equals(authDate) && 8 == authDate.trim().length()) {
                    System.out.println("AuthDate:" + authDate);
                    return authDate;
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
    }

    public static LicenseModule getLicenseModule(String productKey, String moduleKey) throws Exception {
        if (null != licenseMap && 0 != licenseMap.size()) {
            HashMap<String, LicenseModule> productLicense = (HashMap)licenseMap.get(productKey);
            if (null != productLicense && 0 != productLicense.size()) {
                LicenseModule moduleLicense = (LicenseModule)productLicense.get(moduleKey);
                return moduleLicense;
            }
        }

        return null;
    }

    private static void loadLicense(String licPath) {
        LICENSE_UPDATE_FLAG = true;

    }

    private static void praseLicense() {
        System.out.println("malachi praseLicense ....");
        if (null != licenseString && !"".equals(licenseString)) {
            System.out.println("malachi praseLicense ....licenseString =" + licenseString);
            licenseMap = new HashMap();
            String licStr;
            if (-1 != licenseString.indexOf(ITMANAGER_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + ITMANAGER_PRODUCT_KEY + "]") + ITMANAGER_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + ITMANAGER_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(ITSM_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(ITSM_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + ITSM_PRODUCT_KEY + "]") + ITSM_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + ITSM_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(ITSM_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(ITOM_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + ITOM_PRODUCT_KEY + "]") + ITOM_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + ITOM_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(ITOM_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(UNIVIEW_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + UNIVIEW_PRODUCT_KEY + "]") + UNIVIEW_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + UNIVIEW_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(UNIVIEW_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(ORMS_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + ORMS_PRODUCT_KEY + "]") + ORMS_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + ORMS_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(ORMS_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(NTAS_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + NTAS_PRODUCT_KEY + "]") + NTAS_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + NTAS_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(NTAS_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(VIRTUAL_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + VIRTUAL_PRODUCT_KEY + "]") + VIRTUAL_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + VIRTUAL_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(VIRTUAL_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(IDC_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + IDC_PRODUCT_KEY + "]") + IDC_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + IDC_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(IDC_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(CESHU_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + CESHU_PRODUCT_KEY + "]") + CESHU_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + CESHU_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(CESHU_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(ITSQM_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + ITSQM_PRODUCT_KEY + "]") + ITSQM_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + ITSQM_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(ITSQM_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(EXTEND1_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + EXTEND1_PRODUCT_KEY + "]") + EXTEND1_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + EXTEND1_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(EXTEND1_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(EXTEND2_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + EXTEND2_PRODUCT_KEY + "]") + EXTEND2_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + EXTEND2_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(EXTEND2_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(EXTEND3_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + EXTEND3_PRODUCT_KEY + "]") + EXTEND3_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + EXTEND3_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(EXTEND3_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }

            if (-1 != licenseString.indexOf(EXTEND4_PRODUCT_KEY)) {
                licStr = licenseString.substring(licenseString.indexOf("[" + EXTEND4_PRODUCT_KEY + "]") + EXTEND4_PRODUCT_KEY.length() + 2, licenseString.indexOf("[/" + EXTEND4_PRODUCT_KEY + "]"));
                if (null != licStr && !"".equals(licStr.trim())) {
                    licenseMap.put(EXTEND4_PRODUCT_KEY, praseProductLicense(licStr.trim()));
                }
            }
        }

    }

    private static HashMap<String, LicenseModule> praseProductLicense(String licString) {
        String[] licStrArray = licString.split("#");
        if (null != licStrArray && 0 != licStrArray.length) {
            HashMap modules = new HashMap();

            for(int i = 0; i < licStrArray.length; ++i) {
                String licStr = licStrArray[i];
                if (null == licStr || "".equals(licStr.trim())) {
                    break;
                }

                licStr = licStr.trim();
                String[] moduleStrArr = licStr.split(":");
                if (null == moduleStrArr || 0 >= moduleStrArr.length || null == moduleStrArr[0] || "".equals(moduleStrArr[0].trim())) {
                    break;
                }

                LicenseModule module = new LicenseModule();
                String[] mkStrArr = moduleStrArr[0].trim().split("\\$");
                if (null == mkStrArr || 0 == mkStrArr.length || null == mkStrArr[0] || "".equals(mkStrArr[0].trim())) {
                    break;
                }

                module.setModuleKey(mkStrArr[0].trim());
                if (2 == mkStrArr.length && null != mkStrArr[1] && MODULE_ON.equals(mkStrArr[1].trim())) {
                    module.setModuleEnable(true);
                } else {
                    module.setModuleEnable(false);
                }

                int moduleCount;
                if (2 == moduleStrArr.length && null != moduleStrArr[1] && !"".equals(moduleStrArr[1])) {
                    try {
                        String[] mkStrSubArr = moduleStrArr[1].trim().split("\\$");
                        if (mkStrSubArr.length == 2) {
                            moduleCount = Integer.parseInt(mkStrSubArr[0].trim());
                            if (MODULE_ON.equals(mkStrSubArr[1].trim())) {
                                module.setModuleEnable(true);
                            } else {
                                module.setModuleEnable(false);
                            }
                        } else {
                            moduleCount = Integer.parseInt(moduleStrArr[1].trim());
                        }
                    } catch (Exception var10) {
                        moduleCount = 0;
                    }
                } else {
                    moduleCount = 0;
                }

                module.setModuleCount(moduleCount);
                modules.put(module.getModuleKey(), module);
            }

            return modules;
        } else {
            return null;
        }
    }
}
