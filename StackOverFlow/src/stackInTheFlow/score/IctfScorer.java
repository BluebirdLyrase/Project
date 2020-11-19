package stackInTheFlow.score;

import stackInTheFlow.score.stat.Stat;
import stackInTheFlow.score.stat.terms.TermStatComponent;

import java.util.Optional;

/**
 * Created by chase on 2/13/17.
 */
public class IctfScorer extends AbstractScorer {

    public IctfScorer(TermStatComponent statComponent) {
        super(statComponent);
    }

    @Override
    public double score(String term) {

        Optional<Stat> termStatOptional = statComponent.getTermStat(term);

        if (termStatOptional.isPresent()) {
            Stat termStat = termStatOptional.get();

            return termStat.getIctf();
        }

        return 0;
    }
}
