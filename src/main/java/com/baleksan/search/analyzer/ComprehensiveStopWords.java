package com.baleksan.search.analyzer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:baleksan@yammer-inc.com" boris/>
 */
public class ComprehensiveStopWords implements StopWords {

    //from Lucene's StopAnalyzer
    private static final String[] ENGLISH_STOP_WORDS = {
            "a", "an", "and", "are", "as", "at", "be", "but", "by",
            "for", "if", "in", "into", "is", "it",
            "no", "not", "of", "on", "or", "such",
            "that", "the", "their", "then", "there", "these",
            "they", "this", "to", "was", "will", "with"
    };

    private static final String[] GENERIC_TIME_CONSTRUCTS = {
            "monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday", "january", "february", "march",
            "april", "may", "june", "july", "august", "september", "october", "november", "december"
    };

    //200 plus most frequent words in English according to Reuters corpus
    //this list is sorted in order of occurence, not alphabetically
    // http://www.cs.ualberta.ca/~lindek/downloads.htm
    private static final String[] REUTERS_FREQUENT_WORDS = {
            "the", "of", "to", "and", "a",
            "in", "-", "for", "'s", "that",
            "is",
            "on",
            "said",
            "with",
            "was",
            "by",
            "at",
            "as",
            "be",
            "from",
            "it",
            "are",
            "has",
            "have",
            "he",
            "will",
            "an",
            "not",
            "his",
            "or",
            "i",
            "this",
            "its",
            "which",
            "were",
            "would",
            "who",
            "had",
            "their",
            "but",
            "they",
            "been",
            "more",
            "about",
            "up",
            "year",
            "one",
            "million",
            "percent",
            "also",
            "than",
            "it",
            "in",
            "two",
            "a",
            "after",
            "all",
            "can",
            "other",
            "new",
            "out",
            "we",
            "but",
            "last",
            "he",
            "first",
            "you",
            "when",
            "into",
            "over",
            "government",
            "people",
            "new",
            "u.s.",
            "some",
            "years",
            "there",
            "no",
            "company",
            "time",
            "by",
            "market",
            "only",
            "could",
            "we",
            "if",
            "them",
            "because",
            "news",
            "any",
            "most",
            "her",
            "what",
            "against",
            "so",
            "three",
            "between",
            "c",
            "this",
            "such",
            "do",
            "now",
            "may",
            "she",
            "should",
            "billion",
            "before",
            "united",
            "made",
            "says",
            "under",
            "down",
            "week",
            "like",
            "limited",
            "per",
            "just",
            "state",
            "times",
            "through",
            "make",
            "him",
            "many",
            "use",
            "since",
            "where",
            "they",
            "being",
            "our",
            "for",
            "country",
            "tuesday",
            "system",
            "wednesday",
            "friday",
            "those",
            "group",
            "during",
            "president",
            "monday",
            "thursday",
            "next",
            "get",
            "these",
            "while",
            "still",
            "off",
            "officials",
            "much",
            "work",
            "president",
            "back",
            "end",
            "day",
            "way",
            "say",
            "today",
            "even",
            "business",
            "ap",
            "very",
            "states",
            "companies",
            "if",
            "take",
            "told",
            "world",
            "expected",
            "service",
            "well",
            "did",
            "there",
            "public",
            "american",
            "economic",
            "shares",
            "part",
            "including",
            "used",
            "and",
            "both",
            "good",
            "second",
            "then",
            "news",
            "set",
            "report",
            "share",
            "how",
            "must",
            "same",
            "national",
            "prices",
            "sales",
            "months",
            "four",
            "may",
            "san",
            "each",
            "going",
            "here",
            "high",
            "bank",
            "rate",
            "number",
            "another",
            "trade",
            "political", "june", "foreign", "meeting", "points", "think"};


    private static final String[] ADDITIONAL_FREQUENT_WORDS = {
            "a", "able", "about", "above", "according", "accordingly", "across",
            "actually", "after", "afterwards", "again", "against", "all", "allow",
            "allows", "almost", "alone", "along", "already", "also", "although",
            "always", "am", "among", "amongst", "an", "and", "another", "any",
            "anybody", "anyhow", "anyone", "anything", "anyway", "anyways", "anywhere",
            "apart", "appear", "appreciate", "appropriate", "are", "around", "as",
            "aside", "ask", "asking", "associated", "at", "available", "away", "awfully",
            "be", "became", "because", "become", "becomes", "becoming", "been",
            "before", "beforehand", "behind", "being", "believe", "below", "beside",
            "besides", "best", "better", "between", "beyond", "both", "brief", "but", "by",
            "came", "can", "cannot", "cant", "cause", "causes", "certain",
            "certainly", "changes", "clearly", "co", "com", "come", "comes", "concerning",
            "consequently", "consider", "considering", "contain", "containing", "contains",
            "corresponding", "could", "course", "currently",
            "definitely", "described", "despite", "did", "different", "do",
            "does", "doing", "done", "down", "downwards", "during",
            "each", "edu", "eg", "eight", "either", "else", "elsewhere",
            "enough", "entirely", "especially", "et", "etc", "even", "ever", "every",
            "everybody", "everyone", "everything", "everywhere", "ex", "exactly", "example", "except",
            "far", "few", "fifth", "first", "five", "followed", "following",
            "follows", "for", "former", "formerly", "forth", "four", "from", "further", "furthermore",
            "general", "get", "gets", "getting", "given", "gives", "go", "goes", "going", "gone", "got", "gotten", "greetings",
            "had", "happens", "hardly", "has", "have", "having", "he", "hello", "help", "hence", "her",
            "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "hi", "him", "himself", "his",
            "hither", "hopefully", "how", "howbeit", "however",
            "ie", "if", "ignored", "immediate", "in", "inasmuch", "inc", "indeed", "indicate",
            "indicated", "indicates", "inner", "insofar", "instead", "into", "inward", "is", "it", "its", "itself",
            "just",
            "keep", "keeps", "kept", "know", "knows", "known",
            "last", "lately", "later", "latter", "latterly", "least", "less", "lest",
            "let", "like", "liked", "likely", "little", "look", "looking", "looks", "ltd",
            "mainly", "many", "may", "maybe", "me", "mean", "meanwhile",
            "merely", "might", "more", "moreover", "most", "mostly", "much", "must", "my", "myself",
            "name", "namely", "nd", "near", "nearly", "necessary", "need", "needs", "neither", "never",
            "nevertheless", "new", "next", "nine", "no", "nobody", "non", "none", "noone", "nor",
            "normally", "not", "nothing", "novel", "now", "nowhere",
            "obviously", "of", "off", "often", "oh", "ok", "okay", "old", "on", "once", "one", "ones",
            "only", "onto", "or", "other", "others", "otherwise", "ought", "our", "ours", "ourselves",
            "out", "outside", "over", "overall", "own",
            "particular", "particularly", "per", "perhaps", "placed", "please", "plus", "possible", "presumably",
            "probably", "provides",
            "que", "quite", "qv",
            "rather", "rd", "re", "really", "reasonably", "regarding", "regardless", "regards", "relatively",
            "respectively", "right",
            "said", "same", "saw", "say", "saying", "says", "second", "secondly", "see", "seeing",
            "seem", "seemed", "seeming",
            "seems", "seen", "self", "selves", "sensible", "sent", "serious", "seriously",
            "seven", "several", "shall", "she", "should", "since", "six", "so", "some",
            "somebody", "somehow", "someone", "something", "sometime", "sometimes",
            "somewhat", "somewhere", "soon", "sorry", "specified", "specify", "specifying",
            "still", "sub", "such", "sup", "sure",
            "take", "taken", "tell", "tends", "th", "than", "thank", "thanks", "thanx", "that",
            "thats", "the", "their", "theirs", "them", "themselves", "then", "thence", "there", "thereafter",
            "thereby", "therefore", "therein", "theres", "thereupon", "these", "they", "think",
            "third", "this", "thorough", "thoroughly", "those", "though", "three",
            "through", "throughout", "thru", "thus", "to", "together", "too", "took", "toward",
            "towards", "tried", "tries", "truly", "try", "trying", "twice", "two",
            "un", "under", "unfortunately", "unless", "unlikely", "until", "unto", "up",
            "upon", "us", "use", "used", "useful", "uses", "using", "usually", "uucp",
            "value", "various", "very", "via", "viz", "vs",
            "want", "wants", "was", "way", "we", "welcome", "well", "went", "were", "what", "whatever",
            "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby",
            "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who",
            "whoever", "whole", "whom", "whose", "why", "will", "willing", "wish", "with",
            "within", "without", "wonder", "would", "would",
            "yes", "yet", "you", "your", "yours", "yourself", "yourselves",
            "zero"};

    private static Set<String> stopWords;

    static {
        stopWords = new HashSet<String>();

        stopWords.addAll(Arrays.asList(ADDITIONAL_FREQUENT_WORDS));
        stopWords.addAll(Arrays.asList(REUTERS_FREQUENT_WORDS));
        stopWords.addAll(Arrays.asList(ENGLISH_STOP_WORDS));
        stopWords.addAll(Arrays.asList(GENERIC_TIME_CONSTRUCTS));
    }

    public boolean isStopWord(String word) {
        return stopWords.contains(word.toLowerCase());
    }

    public Set<String> getStopWords() {
        return stopWords;
    }
}