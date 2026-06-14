package com.ka.api.constants;

/**
 * Known knowledge-base documents (assessment catalog).
 */
public final class DocumentCatalog {

    public static final String D001_AMERICAS_TRAVEL = "D-001";
    public static final String D002_EMEA_TRAVEL = "D-002";
    public static final String D003_APAC_TRAVEL_IN_REVIEW = "D-003";
    public static final String D004_REMOTE_WORK = "D-004";
    public static final String D005_REMOTE_WORK_RETIRED = "D-005";
    public static final String D006_PRODUCTION_DATA = "D-006";
    public static final String D007_COMPENSATION = "D-007";
    public static final String D008_PROCUREMENT = "D-008";
    public static final String D009_VENDOR_DRAFT = "D-009";

    /** Draft, In Review, or Retired — must never appear in answers or citations. */
    public static final String[] NON_APPROVED = {
            D003_APAC_TRAVEL_IN_REVIEW,
            D005_REMOTE_WORK_RETIRED,
            D009_VENDOR_DRAFT
    };

    private DocumentCatalog() {
    }
}
