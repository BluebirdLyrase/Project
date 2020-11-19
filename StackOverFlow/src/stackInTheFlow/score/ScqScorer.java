package stackInTheFlow.score;

import stackInTheFlow.score.stat.Stat;
import stackInTheFlow.score.stat.terms.TermStatComponent;

import java.util.Optional;

/**
 * Created by Chase on 2/11/2017.
 */
public class ScqScorer extends AbstractScorer {

    public ScqScorer(TermStatComponent statComponent) {
        super(statComponent);
    }

    @Override
    public double score(String term) {

        Optional<Stat> termStatOptional = statComponent.getTermStat(term);

        if (termStatOptional.isPresent()) {
            Stat termStat = termStatOptional.get();

            return (1 + Math.log10(termStat.getCtf())) * termStat.getIdf();
        }

        return 0;
    }
}
