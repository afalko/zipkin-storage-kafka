/*
 * Copyright 2015-2018 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin.autoconfigure.storage.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.internal.V2StorageComponent;
import zipkin2.storage.StorageComponent;
import zipkin2.storage.kafka.KafkaStorage;

/**
 * This is autoconfiguration for ZipkinKafka storage.
 */
@Configuration
@EnableConfigurationProperties(ZipkinKafkaStorageProperties.class)
@ConditionalOnProperty(name = "zipkin.storage.type", havingValue = "kafka")
@ConditionalOnMissingBean(StorageComponent.class)
class ZipkinKafkaStorageAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  V2StorageComponent storage(ZipkinKafkaStorageProperties properties) {
    KafkaStorage result = properties.toBuilder().build();
    return V2StorageComponent.create(result);
  }

  @Bean KafkaStorage v2Storage(V2StorageComponent component) {
    return (KafkaStorage) component.delegate();
  }
}