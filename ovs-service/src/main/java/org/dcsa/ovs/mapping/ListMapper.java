package org.dcsa.ovs.mapping;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class ListMapper {
  public static <S, T> List<T> convertList(List<S> src, Function<S, T> mapFunction) {
    return src.stream().map(mapFunction).collect(Collectors.toList());
  }
}
