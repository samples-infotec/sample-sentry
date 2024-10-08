function warn(msg: string, err?: Error) {
  if (typeof console !== 'undefined') {
    console.warn('[vue-i18n] ' + msg);
    if (err) {
      console.warn(err.stack);
    }
  }
}

function isObject(obj: any): boolean {
  return obj !== null && typeof obj === 'object';
}

type Token = {
  type: 'text' | 'named' | 'list' | 'unknown';
  value: string;
};

const RE_TOKEN_LIST_VALUE = /^(?:\d)+/;
const RE_TOKEN_NAMED_VALUE = /^(?:\w)+/;

// as we can't extend the default vue-i18n BaseFormatter we create a own copy of that class with required changes
export default class CoreFormatter {
  private caches: { [key: string]: Array<Token> };
  private leftBraces: RegExp;
  private rightBraces: RegExp;

  constructor() {
    this.caches = Object.create(null);
    this.leftBraces = /{{ |{{/g;
    this.rightBraces = / }}|}}/g;
  }

  interpolate(message: string, values: any): Array<any> {
    if (!values) {
      return [message];
    }
    // this is custom jhipster logic such that we can reuse the i18n json files for vue-i18n
    // replace '{{' with '{' and '{{ ' with '{' and same for '}}'
    message = message.replace(this.leftBraces, '{').replace(this.rightBraces, '}');

    let tokens: Array<Token> = this.caches[message];
    if (!tokens) {
      tokens = parse(message);
      this.caches[message] = tokens;
    }

    return compile(tokens, values);
  }
}

function parse(format: string): Array<Token> {
  const tokens = [];
  let position = 0;

  let text = '';
  while (position < format.length) {
    let char = format[position++];
    if (char === '{') {
      if (text) {
        tokens.push({ type: 'text', value: text });
      }

      text = '';
      let sub = '';
      char = format[position++];
      while (char !== undefined && char !== '}') {
        sub += char;
        char = format[position++];
      }
      const isClosed = char === '}';

      const namedOrUnknown = isClosed && RE_TOKEN_NAMED_VALUE.test(sub) ? 'named' : 'unknown';
      const _type = RE_TOKEN_LIST_VALUE.test(sub) ? 'list' : namedOrUnknown;
      tokens.push({ value: sub, type: _type });
    } else if (char === '%') {
      // when found rails i18n syntax, skip text capture
      if (format[position] !== '{') {
        text += char;
      }
    } else {
      text += char;
    }
  }

  if (text) {
    tokens.push({ type: 'text', value: text });
  }

  return tokens;
}

function compile(tokens: Array<Token>, values: any) {
  const compiled: Array<any> = [];
  let index = 0;

  const namedOrUnknown = isObject(values) ? 'named' : 'unknown';
  const mode: string = Array.isArray(values) ? 'list' : namedOrUnknown;
  if (mode === 'unknown') {
    return compiled;
  }

  while (index < tokens.length) {
    const token = tokens[index];
    switch (token.type) {
      case 'text':
        compiled.push(token.value);
        break;
      case 'list':
        compiled.push(values[parseInt(token.value, 10)]);
        break;
      case 'named':
        if (mode === 'named') {
          compiled.push(values[token.value]);
        } else {
          if (process.env.NODE_ENV !== 'production') {
            warn("Type of token '" + token.type + "' and format of value '" + mode + "' don't match!");
          }
        }
        break;
      case 'unknown':
        if (process.env.NODE_ENV !== 'production') {
          warn("Detect 'unknown' type of token!");
        }
        break;
    }
    index++;
  }

  return compiled;
}
