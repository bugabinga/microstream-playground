package main;

import one.microstream.configuration.types.ByteSize;
import one.microstream.configuration.types.ByteUnit;
import one.microstream.storage.embedded.configuration.types.EmbeddedStorageConfiguration;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

import java.lang.System.Logger;

import static java.lang.System.Logger.Level.INFO;

public interface Main
{
  Logger LOGGER = System.getLogger( "root" );

  static void main( final String[] args )
  {
    final var applicationState = new ApplicationState("my data!");
    try ( final EmbeddedStorageManager storage = EmbeddedStorageConfiguration.Builder()
        .setStorageDirectory( "database" )
        .setChannelCount( 16 )
        .setChannelDirectoryPrefix( "chunk" )
        .setDataFileMaximumSize( ByteSize.New( 1024, ByteUnit.MiB ) )
        .setDataFilePrefix( "data" )
        .createEmbeddedStorageFoundation()
        .setRoot( applicationState )
        .createEmbeddedStorageManager()
        .start() )
    {
      LOGGER.log( INFO, "data: {0}", applicationState.data() );
    }
  }

  record ApplicationState( String data )
  {
  }
}
