package demo.law;

import jakarta.persistence.*;

@Entity
public class Clause {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) public Long id;
    public String articleNumber;
    public String title;
    @Column(length = 3000) public String content;
    @Column(length = 1000) public String keywords;
    public String documentTitle;
    public String documentVersion;
    public String sourceUrl;
    public String verificationStatus;
    protected Clause() {}
    Clause(String articleNumber, String title, String content, String keywords) {
        this.articleNumber=articleNumber; this.title=title; this.content=content; this.keywords=keywords;
        this.documentTitle="中华人民共和国民法典"; this.documentVersion="2020年版（需核验）";
        this.sourceUrl="https://wb.flk.npc.gov.cn/flfg/PDF/bd53dd912c1048f2aecbaa229238334b.pdf";
        this.verificationStatus="待人工核验；仅教学演示";
    }
}
