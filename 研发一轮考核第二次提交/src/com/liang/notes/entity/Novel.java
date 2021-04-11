package com.liang.notes.entity;

/**
 * 笔记实体类别
 * @author 86178
 *
 */
public class Novel {

    /**
     * 编号
     */
    private int id;
    /**
     * 笔记作者
     */
    private String Novelauthor;
    /**
     * 笔记标题
     */
    private String NovelTitle;
    /**
     * 笔记内容
     */
    private String Novelcontent;

    /**
     * 父类构造方法
     */
    public Novel() {
        super();
    }

    /**
     * 子类构造方法
     * @param novelTitle
     * @param novelcontent
     */
    public Novel(String novelauthor,String novelTitle, String novelcontent) {
        super();
        Novelauthor = novelauthor;
        NovelTitle = novelTitle;
        Novelcontent = novelcontent;
    }

    /**
     * 只有名字的构造方法
     * @param novelTitle
     */
    public Novel(String novelTitle) {
        super();
        NovelTitle = novelTitle;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNovelTitle() {
        return NovelTitle;
    }
    public void setNovelTitle(String novelTitle) {
        NovelTitle = novelTitle;
    }
    public String getNovelcontent() {
        return Novelcontent;
    }
    public void setNovelcontent(String novelcontent) {
        Novelcontent = novelcontent;
    }

    public String getNovelauthor() {
        return Novelauthor;
    }

    public void setNovelauthor(String novelauthor) {
        Novelauthor = novelauthor;
    }
}
