package cn.edu.zucc.se2020g11.api.entity;

public class GrammarErrorEntity {
    private Integer grammerErrorId;

    private Integer compositionId;

    private String text;

    public Integer getGrammerErrorId() {
        return grammerErrorId;
    }

    public void setGrammerErrorId(Integer grammerErrorId) {
        this.grammerErrorId = grammerErrorId;
    }

    public Integer getCompositionId() {
        return compositionId;
    }

    public void setCompositionId(Integer compositionId) {
        this.compositionId = compositionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text == null ? null : text.trim();
    }
}