package eclipse_projet_bdda_chaabnia_fekihhassen_benmansour_nadarajah;

public class PageId {
    private int fileId;
    private int pageId;

    public PageId(int fileId, int pageId) {
        this.fileId = fileId;
        this.pageId = pageId;
    }

    public int getFileId() {
        return fileId;
    }

    public int getPageId() {
        return pageId;
    }

    @Override
    public String toString() {
        return "PageId{" + "File Id=" + fileId + ", Numero de page=" + pageId + '}';
    }
}