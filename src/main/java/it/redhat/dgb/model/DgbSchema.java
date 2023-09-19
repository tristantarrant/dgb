package it.redhat.dgb.model;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.AutoProtoSchemaBuilder;

@AutoProtoSchemaBuilder(includeClasses = SftRec.class,
                        schemaPackageName = "it.redhat.dgb",
                        schemaFilePath = "proto",
                        schemaFileName = "dgb.proto")
public interface DgbSchema extends GeneratedSchema{
}
