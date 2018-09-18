# Semantic Similarity

This project was developed as a group project with another teammate for CS:2230:Data Structures in fall 2017.

The objective of the project was to build a Java application to determine similarity between words using following similarity measures:
- Cosine similarity
- Euclidean distance between vectors
- Euclidean distance between normalized vectors

 **Used following features in Java:**
 - Data Structures
 - Iterators
 - Polymorphism
 - Inheritance
 
 Given below is the introduction for the assignment
 
 **Introduction**
 
In this project, you will write an application that understands the semantic similarity, or
closeness of meaning, between two words. For example, the semantic similarity of "car" and
"vehicle" is high, while the semantic similarity of "car" and "flower" is low. Semantic similarity
has many uses, including finding synonyms, inferring sentiment, and inferring topic.
To compute the semantic similarity of two words, you will first compute each word's semantic
descriptor vector. Then you will compute the closeness of two vectors using a similarity
measure, such as cosine similarity or Euclidean distance.

#### Semantic descriptor vector ####
Given a text with n unique words denoted by (w1, w2, ..., wn) and a word w, let descw be the
semantic descriptor vector of w computed using the text. descw is an n-dimensional vector. The
i-th coordinate of descw is the number of sentences in which both w and wi occur.
- For example, suppose we are given the following text (the opening of Notes from the
Underground by Fyodor Dostoyevsky, translated by Constance Garnett):
- I am a sick man. I am a spiteful man. I am an unattractive man. I believe my liver is diseased.
However, I know nothing at all about my disease, and do not know for certain what ails me.
- For this text, the dimensions of the semantic descriptor vectors will be
* *I, am, a, sick, man, spiteful, an, unattractive, believe, my, liver, is, diseased, However, know,
nothing, at, all, about, disease, and, do, not, for, certain, what, ails, me* *
- That is, 1 dimension for each unique word of the text.
- The word “man” appears in the first three sentences. Its semantic descriptor vector would be:
* *I=3, am=3, a=2, sick=1, man=0, spiteful=1, an=1, unattractive=1, believe=0, my=0, liver=0, is=0,
diseased=0, However=0, know=0, nothing=0, at=0, all=0, about=0, disease=0, and=0, do=0,
not=0, for=0, certain=0, what=0, ails=0, me=0* *
- The word “liver” occurs in the fourth sentence, so its semantic descriptor vector is:
* *I=1, am=0, a=0, sick=0, man=0, spiteful=0, an=0, unattractive=0, believe=1, my=1, liver=0, is=1,
diseased=1, However=0, know=0, nothing=0, at=0, all=0, about=0, disease=0, and=0, do=0,
not=0, for=0, certain=0, what=0, ails=0, me=0* *
####Cosine similarity####
The cosine similarity between two vectors 𝑢 = (𝑢1, 𝑢2, . . . , 𝑢n) ) and 𝑣 = (𝑣1, 𝑣2, . . . , 𝑣n)) is
defined as:
#### 𝑠𝑖n (𝑢, 𝑣) = 𝑢 ∙ 𝑣 / |u|.|v| = Sum(ui . vi) / Sqrt( Sum (ui^2 . vi^2)) ####
