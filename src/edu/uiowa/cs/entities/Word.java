
package edu.uiowa.cs.entities;


public class Word {

    private String word_content;
    private int sentence_count;

    /**
     * @return the word_content
     */
    public String getWord_content() {
        return word_content;
    }

    /**
     * @param word_content the word_content to set
     */
    public void setWord_content(String word_content) {
        this.word_content = word_content;
    }

    /**
     * @return the sentence_count
     */
    public int getSentence_count() {
        return sentence_count;
    }

    /**
     * @param sentence_count the sentence_count to set
     */
    public void setSentence_count(int sentence_count) {
        this.sentence_count = sentence_count;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Word) {
            Word w = (Word) o;
            return this.getWord_content().equalsIgnoreCase(w.getWord_content()) && this.getSentence_count()==w.getSentence_count();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getWord_content().hashCode() + this.getSentence_count();
    }

}
