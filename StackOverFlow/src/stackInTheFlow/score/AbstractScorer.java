package stackInTheFlow.score;

import stackInTheFlow.score.stat.terms.TermStatComponent;

/**
 * Created by Chase on 2/11/2017.
 */
public abstract class AbstractScorer implements Scorer {

    protected TermStatComponent statComponent;

    public AbstractScorer(TermStatComponent statComponent) {
        this.statComponent = statComponent;
    }

}
