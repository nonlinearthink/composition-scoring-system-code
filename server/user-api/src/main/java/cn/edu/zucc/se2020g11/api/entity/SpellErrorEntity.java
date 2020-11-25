package cn.edu.zucc.se2020g11.api.entity;

public class SpellErrorEntity {
    private Integer spellErrorId;

    private Integer compositionId;

    private String text;

    public Integer getSpellErrorId() {
        return spellErrorId;
    }

    public void setSpellErrorId(Integer spellErrorId) {
        this.spellErrorId = spellErrorId;
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