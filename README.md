# INITools
A simple way to manage .ini files in java, comes in three classes a Reader, a Writer and a settings class

INIReader: just call the <code>getSettings()</code> function after proper call of the constructor

INIWriter: instanciate a new object with a proper INISettings object and then call its <code>write()</code> function to serialize it

INISettings: this objects act like a map of map for every field and subfield. call its getters and setters to set the names and values of each propriety
