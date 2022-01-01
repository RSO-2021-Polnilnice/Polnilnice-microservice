package si.fri.rso.polnilnice.graphql;

import com.kumuluz.ee.graphql.annotations.GraphQLClass;
import com.kumuluz.ee.graphql.classes.Filter;
import com.kumuluz.ee.graphql.classes.Pagination;
import com.kumuluz.ee.graphql.classes.PaginationWrapper;
import com.kumuluz.ee.graphql.classes.Sort;
import com.kumuluz.ee.graphql.utils.GraphQLUtils;
import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLQuery;
import si.fri.rso.polnilnice.lib.Polnilnica;
import si.fri.rso.polnilnice.services.beans.PolnilnicaBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@GraphQLClass
@ApplicationScoped
public class PolnilniceQueries {
    @Inject
    private PolnilnicaBean polnilnicaBean;

    @GraphQLQuery
    public PaginationWrapper<Polnilnica> allImageMetadata(@GraphQLArgument(name = "pagination") Pagination pagination,
                                                          @GraphQLArgument(name = "sort") Sort sort,
                                                          @GraphQLArgument(name = "filter") Filter filter) {

        return GraphQLUtils.process(polnilnicaBean.getPolnilnice(), pagination, sort, filter);
    }

    @GraphQLQuery
    public Polnilnica getPolnilnica(@GraphQLArgument(name = "id") Integer id) {
        return polnilnicaBean.getPolnilnicaById(id);
    }

}
