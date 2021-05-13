// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// https://developers.google.com/protocol-buffers/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.google.protobuf;

import java.util.Set;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;

/**
 * A table of known extensions, searchable by name or field number. When parsing a protocol message
 * that might have extensions, you must provide an {@code ExtensionRegistry} in which you have
 * registered any extensions that you want to be able to parse. Otherwise, those extensions will
 * just be treated like unknown fields.
 *
 * <p>For example, if you had the {@code .proto} file:
 *
 * <pre>
 * option java_class = "MyProto";
 *
 * message Foo {
 *   extensions 1000 to max;
 * }
 *
 * extend Foo {
 *   optional int32 bar;
 * }
 * </pre>
 *
 * Then you might write code like:
 *
 * <pre>
 * ExtensionRegistry registry = ExtensionRegistry.newInstance();
 * registry.add(MyProto.bar);
 * MyProto.Foo message = MyProto.Foo.parseFrom(input, registry);
 * </pre>
 *
 * <p>Background:
 *
 * <p>You might wonder why this is necessary. Two alternatives might come to mind. First, you might
 * imagine a system where generated extensions are automatically registered when their containing
 * classes are loaded. This is a popular technique, but is bad design; among other things, it
 * creates a situation where behavior can change depending on what classes happen to be loaded. It
 * also introduces a security vulnerability, because an unprivileged class could cause its code to
 * be called unexpectedly from a privileged class by registering itself as an extension of the right
 * type.
 *
 * <p>Another option you might consider is lazy parsing: do not parse an extension until it is first
 * requested, at which point the caller must provide a type to use. This introduces a different set
 * of problems. First, it would require a mutex lock any time an extension was accessed, which would
 * be slow. Second, corrupt data would not be detected until first access, at which point it would
 * be much harder to deal with it. Third, it could violate the expectation that message objects are
 * immutable, since the type provided could be any arbitrary message class. An unprivileged user
 * could take advantage of this to inject a mutable object into a message belonging to privileged
 * code and create mischief.
 *
 * @author kenton@google.com Kenton Varda
 */
public class ExtensionRegistry extends ExtensionRegistryLite {
  /** Get the unmodifiable singleton empty instance. */
  public static ExtensionRegistry getEmptyRegistry() {
    return EMPTY_REGISTRY;
  }


  /** Returns an unmodifiable view of the registry. */
  @Override
  public ExtensionRegistry getUnmodifiable() {
    return EMPTY_REGISTRY;
  }

  /** A (Descriptor, Message) pair, returned by lookup methods. */
  public static final class ExtensionInfo {
    /** The extension's descriptor. */
    public final FieldDescriptor descriptor = null;

    /**
     * A default instance of the extension's type, if it has a message type. Otherwise, {@code
     * null}.
     */
    public final Message defaultInstance= null;

  }

  /** Deprecated. Use {@link #findImmutableExtensionByName(String)} instead. */
  @Deprecated
  public ExtensionInfo findExtensionByName(final String fullName) { return null; }

  /**
   * Find an extension for immutable APIs by fully-qualified field name, in the proto namespace.
   * i.e. {@code result.descriptor.fullName()} will match {@code fullName} if a match is found.
   *
   * @return Information about the extension if found, or {@code null} otherwise.
   */
  public ExtensionInfo findImmutableExtensionByName(final String fullName) { return null; }

  /**
   * Find an extension for mutable APIs by fully-qualified field name, in the proto namespace. i.e.
   * {@code result.descriptor.fullName()} will match {@code fullName} if a match is found.
   *
   * @return Information about the extension if found, or {@code null} otherwise.
   */
  public ExtensionInfo findMutableExtensionByName(final String fullName) { return null; }

  /** Deprecated. Use {@link #findImmutableExtensionByNumber( Descriptors.Descriptor, int)} */
  @Deprecated
  public ExtensionInfo findExtensionByNumber(
      final Descriptor containingType, final int fieldNumber) { return null; }

  /**
   * Find an extension by containing type and field number for immutable APIs.
   *
   * @return Information about the extension if found, or {@code null} otherwise.
   */
  public ExtensionInfo findImmutableExtensionByNumber(
      final Descriptor containingType, final int fieldNumber) { return null; }

  /**
   * Find an extension by containing type and field number for mutable APIs.
   *
   * @return Information about the extension if found, or {@code null} otherwise.
   */
  public ExtensionInfo findMutableExtensionByNumber(
      final Descriptor containingType, final int fieldNumber) { return null; }

  /**
   * Find all extensions for mutable APIs by fully-qualified name of extended class. Note that this
   * method is more computationally expensive than getting a single extension by name or number.
   *
   * @return Information about the extensions found, or {@code null} if there are none.
   */
  public Set<ExtensionInfo> getAllMutableExtensionsByExtendedType(final String fullName) { return null; }

  /**
   * Find all extensions for immutable APIs by fully-qualified name of extended class. Note that
   * this method is more computationally expensive than getting a single extension by name or
   * number.
   *
   * @return Information about the extensions found, or {@code null} if there are none.
   */
  public Set<ExtensionInfo> getAllImmutableExtensionsByExtendedType(final String fullName) { return null; }

  /** Add an extension from a generated file to the registry. */
  public void add(final Extension<?, ?> extension) { }

  /** Add a non-message-type extension to the registry by descriptor. */
  public void add(final FieldDescriptor type) { }

  /** Add a message-type extension to the registry by descriptor. */
  public void add(final FieldDescriptor type, final Message defaultInstance) { }

  // =================================================================
  // Private stuff.
  
  ExtensionRegistry(boolean empty) {
    super(true);
  }

  static final ExtensionRegistry EMPTY_REGISTRY = new ExtensionRegistry(true);

}
