package com.enjoy.EnjoyClaude.config;

import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.io.CharacterEscapes;
import com.fasterxml.jackson.core.io.SerializedString;
import org.apache.commons.text.StringEscapeUtils;

/**
 * HTML 특수문자 이스케이프 처리
 * XSS 공격 방지를 위한 커스텀 CharacterEscapes
 */
public class HtmlCharacterEscapes extends CharacterEscapes {
    private final int[] asciiEscapes;

    public HtmlCharacterEscapes() {
        // 기본 이스케이프 배열 초기화
        asciiEscapes = CharacterEscapes.standardAsciiEscapesForJSON();

        // HTML 특수문자 추가 이스케이프 설정
        asciiEscapes['<'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['>'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['&'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\"'] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['\''] = CharacterEscapes.ESCAPE_CUSTOM;
        asciiEscapes['/'] = CharacterEscapes.ESCAPE_CUSTOM;
    }

    @Override
    public int[] getEscapeCodesForAscii() {
        return asciiEscapes;
    }

    @Override
    public SerializableString getEscapeSequence(int ch) {
        // Apache Commons Text를 사용한 HTML 이스케이프
        return new SerializedString(StringEscapeUtils.escapeHtml4(Character.toString((char) ch)));
    }
}
