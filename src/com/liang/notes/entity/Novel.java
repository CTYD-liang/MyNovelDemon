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
    private String novelAuthor;
    /**
     * 笔记标题
     */
    private String novelTitle;
    /**
     * 笔记内容
     */
    private String novelContent;

    /**
     * 父类构造方法
     */
    public Novel() {
        super();
    }

    /**
     * 子类构造方法
     * @param novelTitle
     * @param novelContent
     */
    public Novel(String novelAuthor,String novelTitle, String novelContent) {
        super();
        this.novelAuthor = novelAuthor;
        this.novelTitle = novelTitle;
        this.novelContent = novelContent;
    }

    /**
     * 只有名字的构造方法
     * @param novelTitle
     */
    public Novel(String novelTitle) {
        super();
        this.novelTitle = novelTitle;
    }

    /**
     * 作者姓名和笔记标题
     * @param author
     * @param novelTitle
     */
    public Novel(String author,String novelTitle){
        this.novelAuthor = author;
        this.novelTitle = novelTitle;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNovelTitle() {
        return novelTitle;
    }
    public void setNovelTitle(String novelTitle) {
        this.novelTitle = novelTitle;
    }
    public String getNovelcontent() {
        return novelContent;
    }
    public void setNovelcontent(String novelcontent) {
        this.novelContent = novelcontent;
    }

    public String getNovelauthor() {
        return novelAuthor;
    }

    public void setNovelauthor(String novelauthor) {
        this.novelAuthor = novelAuthor;
    }
}
