package br.com.attornatus.resources.services.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PageableServiceUtil {

    private Integer page;
    private Integer size;
    private String targetSort;
    private SortType sortType;

    public enum SortType {
        ASCENDING,
        DESCENDING
    }

    public PageableServiceUtil targetSort(String targetSort) {
        this.targetSort = targetSort;
        return this;
    }

    public PageableServiceUtil sortType(SortType type) {
        this.sortType = type;
        return this;
    }

    public Pageable buildPageable(Map<String, String> pageParams) {
        setPageAndSize(pageParams);
        return buildPageableOnSort();
    }

    private void throwExceptionNotFoundPageParams(Map<String, String> pageParams) {
        boolean noExistPageKey = !pageParams.containsKey("page");
        boolean noExistSizeKey = !pageParams.containsKey("size");

        if (noExistPageKey || noExistSizeKey) {
            throw new IllegalArgumentException("Não foi possivel localizar a `página` e `quantidade de elementos na " +
                    "`página`.");
        }
    }

    private void throwExceptionBlankPageParams(String page, String size) {
        boolean isBlankPage = page.isBlank();
        boolean isBlankSize = size.isBlank();

        if (isBlankPage || isBlankSize) {
            throw new IllegalArgumentException("Não foi possivel encontrar os valores da `page` e `size`.");
        }
    }

    private void throwExceptionNotNumericPageParams(String page, String size) {
        boolean isPageNotNumeric = !checkFieldIsNumeric(page);
        boolean isSizeNotNumeric = !checkFieldIsNumeric(size);

        if (isPageNotNumeric || isSizeNotNumeric) {
            throw new IllegalArgumentException("Não é possivel determinar se os parâmetros `page` e `size` como " +
                    "valores numéricos.");
        }
    }

    private void throwExceptionPageParamsAreLessThanZero() {
        boolean isPageLessThanZero = this.page < 0;
        boolean isSizeLessThanZero = this.size < 0;

        if (isPageLessThanZero || isSizeLessThanZero) {
            throw new IllegalArgumentException("Não é possivel realizar a operação com os parâmetros `page` e `size` " +
                    "sendo menores do que zero.");
        }
    }

    private boolean checkFieldIsNumeric(String field) {
        try {
            Integer.parseInt(field);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    private void setPageAndSize(Map<String, String> pageParams) {
        throwExceptionNotFoundPageParams(pageParams);

        String pageString = pageParams.get("page");
        String sizeString = pageParams.get("size");
        throwExceptionBlankPageParams(pageString, sizeString);

        throwExceptionNotNumericPageParams(pageString, sizeString);
        this.page = Integer.parseInt(pageString);
        this.size = Integer.parseInt(sizeString);
        throwExceptionPageParamsAreLessThanZero();
    }

    private Sort buildSortAscending(String targetSort) {
        return Sort.by(targetSort).ascending();
    }

    private Sort buildSortDescending(String targetSort) {
        return Sort.by(targetSort).descending();
    }

    private Pageable buildPageableOnSort() {
        Sort sort = Sort.by(targetSort);
        if (sortType.equals(SortType.ASCENDING)) {
            sort.ascending();
        }
        else if (sortType.equals(SortType.DESCENDING)) {
            sort.descending();
        }
        return PageRequest.of(page, size, sort);
    }

}
