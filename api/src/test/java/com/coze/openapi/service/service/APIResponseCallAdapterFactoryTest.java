package com.coze.openapi.service.service;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.coze.openapi.client.common.BaseResponse;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;
import retrofit2.http.Streaming;

public class APIResponseCallAdapterFactoryTest {

  private APIResponseCallAdapterFactory factory;
  private Retrofit retrofit;

  @BeforeEach
  public void setUp() {
    factory = APIResponseCallAdapterFactory.create();
    retrofit = new Retrofit.Builder().baseUrl("https://api.test.com").build();
  }

  @Test
  public void testCreate() {
    APIResponseCallAdapterFactory newFactory = APIResponseCallAdapterFactory.create();
    assertNotNull(newFactory);
  }

  @Test
  public void testGetWithNonCallReturnType() {
    // 测试非 Call 类型的返回值
    Type returnType = String.class;
    Annotation[] annotations = new Annotation[0];

    CallAdapter<?, ?> adapter = factory.get(returnType, annotations, retrofit);
    assertNull(adapter);
  }

  @Test
  public void testGetWithValidCallReturnType() {
    // 测试有效的 Call 返回类型
    Type returnType = createParameterizedType(Call.class, BaseResponse.class);
    Annotation[] annotations = new Annotation[0];

    CallAdapter<?, ?> adapter = factory.get(returnType, annotations, retrofit);
    assertNotNull(adapter);
    assertInstanceOf(APIResponseCallAdapter.class, adapter);
  }

  @Test
  public void testGetWithStreamingAnnotation() {
    // 测试带有 Streaming 注解的情况
    Type returnType = createParameterizedType(Call.class, BaseResponse.class);
    Annotation[] annotations =
        new Annotation[] {
          new Streaming() {
            @Override
            public Class<? extends Annotation> annotationType() {
              return Streaming.class;
            }
          }
        };

    CallAdapter<?, ?> adapter = factory.get(returnType, annotations, retrofit);
    assertNull(adapter);
  }

  @Test
  public void testGetWithParameterizedResponse() {
    // 测试参数化的响应类型
    Type returnType =
        createParameterizedType(
            Call.class, createParameterizedType(BaseResponse.class, String.class));
    Annotation[] annotations = new Annotation[0];

    CallAdapter<?, ?> adapter = factory.get(returnType, annotations, retrofit);
    assertNotNull(adapter);
    assertInstanceOf(APIResponseCallAdapter.class, adapter);
  }

  @Test
  public void testGetWithListResponse() {
    // 测试 List 响应类型
    Type returnType =
        createParameterizedType(Call.class, createParameterizedType(List.class, String.class));
    Annotation[] annotations = new Annotation[0];

    CallAdapter<?, ?> adapter = factory.get(returnType, annotations, retrofit);
    assertNotNull(adapter);
    assertInstanceOf(APIResponseCallAdapter.class, adapter);
  }

  // 辅助方法：创建参数化类型
  private Type createParameterizedType(final Class<?> rawType, final Type... argumentTypes) {
    return new ParameterizedTypeImpl(null, rawType, argumentTypes);
  }

  // 内部类：实现 ParameterizedType 接口
  private static class ParameterizedTypeImpl implements java.lang.reflect.ParameterizedType {
    private final Type ownerType;
    private final Type rawType;
    private final Type[] argumentTypes;

    ParameterizedTypeImpl(Type ownerType, Type rawType, Type... argumentTypes) {
      this.ownerType = ownerType;
      this.rawType = rawType;
      this.argumentTypes = argumentTypes;
    }

    @Override
    public Type[] getActualTypeArguments() {
      return argumentTypes;
    }

    @Override
    public Type getRawType() {
      return rawType;
    }

    @Override
    public Type getOwnerType() {
      return ownerType;
    }
  }
}
