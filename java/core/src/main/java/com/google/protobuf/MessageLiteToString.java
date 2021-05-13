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

/** Helps generate {@link String} representations of {@link MessageLite} protos. */
final class MessageLiteToString {

  /**
   * Returns a {@link String} representation of the {@link MessageLite} object. The first line of
   * the {@code String} representation representation includes a comment string to uniquely identify
   * the object instance. This acts as an indicator that this should not be relied on for
   * comparisons.
   *
   * <p>For use by generated code only.
   */
  static String toString(MessageLite messageLite, String commentString) {
    StringBuilder buffer = new StringBuilder();
    buffer.append("# ").append(commentString);
    reflectivePrintWithIndent(messageLite, buffer, 0);
    return buffer.toString();
  }

  /**
   * Reflectively prints the {@link MessageLite} to the buffer at given {@code indent} level.
   *
   * @param buffer the buffer to write to
   * @param indent the number of spaces to indent the proto by
   */
  private static void reflectivePrintWithIndent(
      MessageLite messageLite, StringBuilder buffer, int indent) {
  }


  /**
   * Formats a text proto field.
   *
   * <p>For use by generated code only.
   *
   * @param buffer the buffer to write to
   * @param indent the number of spaces the proto should be indented by
   * @param name the field name (in lower underscore case)
   * @param object the object value of the field
   */
  static final void printField(StringBuilder buffer, int indent, String name, Object object) {
  }

}
