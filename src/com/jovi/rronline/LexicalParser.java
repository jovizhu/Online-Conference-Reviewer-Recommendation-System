package com.jovi.rronline;
import java.io.StringReader;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.process.TokenizerFactory;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.CoreLabelTokenFactory;
import edu.stanford.nlp.process.PTBTokenizer;
import edu.stanford.nlp.process.Tokenizer;
import edu.stanford.nlp.trees.Tree;

class LexicalParser {

    private final static String PCG_MODEL = "edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz";        

    private final TokenizerFactory<CoreLabel> tokenizerFactory = PTBTokenizer.factory(new CoreLabelTokenFactory(), "invertible=true");

    private final LexicalizedParser parser = LexicalizedParser.loadModel(PCG_MODEL);

    public Tree parse(String str) {                
        List<CoreLabel> tokens = tokenize(str);
        Tree tree = parser.apply(tokens);
        return tree;
    }

    private List<CoreLabel> tokenize(String str) {
        Tokenizer<CoreLabel> tokenizer =
            tokenizerFactory.getTokenizer(
                new StringReader(str));    
        return tokenizer.tokenize();
    }

    public static void main(String[] args) { 
        String str = "This is a rigorous and complete textbook for a first course on information retrieval from the computer science (as opposed to a user-centred) perspective. The advent of the Internet and the enormous increase in volume of electronically stored information generally has led to substantial work on IR from the computer science perspective - this book provides an up-to-date student oriented treatment of the subject.";
        LexicalParser parser = new LexicalParser(); 
        Tree tree = parser.parse(str);  

        List<Tree> leaves = tree.getLeaves();
        // Print words and Pos Tags
        for (Tree leaf : leaves) { 
            Tree parent = leaf.parent(tree);
            System.out.print(leaf.label().value() + "-" + parent.label().value() + " ");
        }
        System.out.println();               
    }
}