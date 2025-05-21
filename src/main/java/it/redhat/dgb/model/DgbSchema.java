package it.redhat.dgb.model;

import org.infinispan.protostream.GeneratedSchema;
import org.infinispan.protostream.annotations.ProtoSchema;

@ProtoSchema(includeClasses = SftRec.class,
                        schemaPackageName = "it.redhat.dgb",
                        schemaFilePath = "proto",
                        schemaFileName = "dgb.proto")
public interface DgbSchema extends GeneratedSchema{
}
