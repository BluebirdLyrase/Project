package stackInTheFlow.score.combiner;

import stackInTheFlow.score.Scorer;

import java.util.Collection;

/**
 * Created by Chase on 2/11/2017.
 */
public abstract class AbstractCombiner implements Combiner {

    protected Collection<Scorer> scorers;

    public AbstractCombiner(Collection<Scorer> scorers) {
        this.scorers = scorers;
    }
}
