[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.chandler/protobuf-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.chandler/protobuf-java) 

Protocol Buffers - Google's data interchange format
===================================================

Copyright 2008 Google Inc.

https://developers.google.com/protocol-buffers/

protobuf-teavm
--------

This repository contains a modified version of protobuf-java 3.7.1 that
compiles and runs successfully under [TeaVM](http://teavm.org/), a JavaScript
compiler for Java bytecode. 

The following modifications have been made for compatibility:
 - Removed all references to sun.misc.Unsafe
   - Unsafe was used to implement optional direct ByteBuffer optimizations
     in the original library.
 - Removed support for direct buffers in CodedInputStream
 - Replaced WeakHashMap usage with regular HashMap
   - WeakHashMap was used in protobuf enums to avoid permanently caching 
     <Integer, EnumValueDescriptor> entries. This change shouldn't negatively
     affect the majority of use cases where proto schemas are loaded and kept in
     use for the lifetime of an application.
 - Incorporated [ISO_8859_1](https://github.com/cjgriscom/protobuf-teavm/blob/master/java/core/src/main/java/org/apache/harmony/niochar/charset/ISO_8859_1.java) 
   support from Apache Harmony
   - As of writing, TeaVM only supports UTF-8 charset.  Protobuf-java requires ISO_8859_1
     in its conversion of strings from byte arrays.

Important considerations:
  - The standard protoc 3.7.1 remains compatible with this library without modification.
  - Protobuf-java appears to fail when ADVANCED or FULL optimizations are turned on in 
    TeaVM. Optimization mode should not exceed SIMPLE.
    
protobuf-teavm is deployed to Maven Central.
```xml
<dependency>
    <groupId>io.chandler</groupId>
    <artifactId>protobuf-java</artifactId>
    <version>3.7.1-teavm1</version>
</dependency>
```

Overview
--------

Protocol Buffers (a.k.a., protobuf) are Google's language-neutral,
platform-neutral, extensible mechanism for serializing structured data. You
can find [protobuf's documentation on the Google Developers site](https://developers.google.com/protocol-buffers/).

This README file contains protobuf installation instructions. To install
protobuf, you need to install the protocol compiler (used to compile .proto
files) and the protobuf runtime for your chosen programming language.

Protocol Compiler Installation
------------------------------

The protocol compiler is written in C++. If you are using C++, please follow
the [C++ Installation Instructions](src/README.md) to install protoc along
with the C++ runtime.

For non-C++ users, the simplest way to install the protocol compiler is to
download a pre-built binary from our release page:

  [https://github.com/protocolbuffers/protobuf/releases](https://github.com/protocolbuffers/protobuf/releases)

In the downloads section of each release, you can find pre-built binaries in
zip packages: protoc-$VERSION-$PLATFORM.zip. It contains the protoc binary
as well as a set of standard .proto files distributed along with protobuf.

If you are looking for an old version that is not available in the release
page, check out the maven repo here:

  [https://repo1.maven.org/maven2/com/google/protobuf/protoc/](https://repo1.maven.org/maven2/com/google/protobuf/protoc/)

These pre-built binaries are only provided for released versions. If you want
to use the github master version at HEAD, or you need to modify protobuf code,
or you are using C++, it's recommended to build your own protoc binary from
source.

If you would like to build protoc binary from source, see the [C++ Installation
Instructions](src/README.md).

Protobuf Runtime Installation
-----------------------------

Protobuf supports several different programming languages. For each programming
language, you can find instructions in the corresponding source directory about
how to install protobuf runtime for that specific language:

| Language                             | Source                                                      | Ubuntu | MacOS | Windows |
|--------------------------------------|-------------------------------------------------------------|--------|-------|---------|
| C++ (include C++ runtime and protoc) | [src](src)                                                  | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-cpp_distcheck.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fcpp_distcheck%2Fcontinuous) [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-bazel.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fbazel%2Fcontinuous) | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fcpp%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-cpp_distcheck.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fcpp_distcheck%2Fcontinuous) | [![Build status](https://ci.appveyor.com/api/projects/status/73ctee6ua4w2ruin?svg=true)](https://ci.appveyor.com/project/protobuf/protobuf) |
| Java                                 | [java](java)                                                | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-java_compatibility.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fjava_compatibility%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-java_jdk7.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fjava_jdk7%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-java_oracle7.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fjava_oracle7%2Fcontinuous) | | |
| Python                               | [python](python)                                            | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python27.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython27%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python33.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython33%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python34.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython34%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python35.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython35%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python36.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython36%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python37.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython37%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python_compatibility.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython_compatibility%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python27_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython27_cpp%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python33_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython33_cpp%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python34_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython34_cpp%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python35_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython35_cpp%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python36_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython36_cpp%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-python37_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fpython37_cpp%2Fcontinuous) | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-python.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fpython%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-python_cpp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fpython_cpp%2Fcontinuous) | |
| Objective-C                          | [objectivec](objectivec)                                    | | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-objectivec_cocoapods_integration.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fobjectivec_cocoapods_integration%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-objectivec_ios_debug.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fobjectivec_ios_debug%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-objectivec_ios_release.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fobjectivec_ios_release%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-objectivec_osx.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fobjectivec_osx%2Fcontinuous) | |
| C#                                   | [csharp](csharp)                                            | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-csharp.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fcsharp%2Fcontinuous) | | [![Build status](https://ci.appveyor.com/api/projects/status/73ctee6ua4w2ruin?svg=true)](https://ci.appveyor.com/project/protobuf/protobuf) |
| JavaScript                           | [js](js)                                                    | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-javascript.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fjavascript%2Fcontinuous) | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-javascript.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fjavascript%2Fcontinuous) | |
| Ruby                                 | [ruby](ruby)                                                | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-ruby23.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fruby23%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-ruby24.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fruby24%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-ruby25.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fruby25%2Fcontinuous) | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-ruby23.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fruby23%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-ruby24.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fruby24%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-ruby25.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fruby25%2Fcontinuous) | |
| Go                                   | [golang/protobuf](https://github.com/golang/protobuf)       | | | |
| PHP                                  | [php](php)                                                  | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-php_all.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2Fphp_all%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/linux-32-bit.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fubuntu%2F32-bit%2Fcontinuous) | [![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-php5.6_mac.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fphp5.6_mac%2Fcontinuous)<br/>[![Build status](https://storage.googleapis.com/protobuf-kokoro-results/status-badge/macos-php7.0_mac.png)](https://fusion.corp.google.com/projectanalysis/current/KOKORO/prod:protobuf%2Fgithub%2Fmaster%2Fmacos%2Fphp7.0_mac%2Fcontinuous) | |
| Dart                                 | [dart-lang/protobuf](https://github.com/dart-lang/protobuf) | [![Build Status](https://travis-ci.org/dart-lang/protobuf.svg?branch=master)](https://travis-ci.org/dart-lang/protobuf) | | |

Quick Start
-----------

The best way to learn how to use protobuf is to follow the tutorials in our
developer guide:

https://developers.google.com/protocol-buffers/docs/tutorials

If you want to learn from code examples, take a look at the examples in the
[examples](examples) directory.

Documentation
-------------

The complete documentation for Protocol Buffers is available via the
web at:

https://developers.google.com/protocol-buffers/
