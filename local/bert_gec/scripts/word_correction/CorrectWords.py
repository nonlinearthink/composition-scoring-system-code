#!/usr/bin/env python
# -*- coding: utf-8 -*-

import re, collections

# special token
UNK = "-unk-"

# find all lower token in corpus
def tokens(text):
    return re.findall("[a-z]+", text.lower())


# open the file, count all words' frequency
# TODO: change the dict_path according to the call path
with open('./word_correction/dictionary.txt', 'r') as f:
    words = tokens(f.read())
word_counts = collections.Counter(words)


# e.g. 'fianlly helloa'
def correct_text_generic(text):
    """
    correct all words in the input text
    """
    return re.sub('[a-zA-Z]+', correct_match, text)


def correct_match(match):
    """ callback function
    use re to match the tokens in th corpus
    """

    word = match.group()

    def case_of(text):
        """
            return lower token
        """
        return (str.upper if text.isupper() else
                str.lower if text.islower() else
                str.title if text.istitle() else
                str)

    return case_of(word)(correct(word.lower()))


def correct(word):
    """
     chose the most similar word, which has the minimal edit distance with original word

     chose rule:
     1. chose the words as candidate words , which has edit distance with original word between in [1,3]
     2. select the most common word as target word
    """
    # union of sets
    candidates = (known(edits0(word)) or
                  known(edits1(word)) or
                  known(edits2(word)))

    ans = UNK
    if len(candidates) != 0:
        ans = max(candidates, key=word_counts.get)
    return ans


def known(words):
    """
    judge whether the word exist in corpus
    """
    return {w for w in words if w in word_counts}


def edits0(word):
    """
    the word self
    """
    return {word}

def edits1(word):
    """
    chose the word which has edit distance 1 with original word
    """
    alphabet = ''.join([chr(ord('a') + i) for i in range(26)])

    def splits(word):
        """ split word
        e.g.
        cta :("","cat")  ("c","at")  ("ca","")   ("cat","")
        """
        return [(word[:i], word[i:])
                for i in range(len(word) + 1)]

    pairs = splits(word)

    # delete
    deletes = [a + b[1:] for (a, b) in pairs if b]
    # exchange
    transposes = [a + b[1] + b[0] + b[2:] for (a, b) in pairs if len(b) > 1]
    # replace
    replaces = [a + c + b[1:] for (a, b) in pairs for c in alphabet if b]
    # insert
    inserts = [a + c + b for (a, b) in pairs for c in alphabet]


    return set(deletes + transposes + replaces + inserts)


def edits2(word):
    """
    chose the word which has edit distance 2 with original word
    """

    return {e2 for e1 in edits1(word) for e2 in edits1(e1)}


if __name__ == '__main__':
    original_word = 'fianlly helloa\n cta sakladkhsadsa!'
    correct_word = correct_text_generic(original_word)
    print('Original word:%s\nCorrect word:%s' % (original_word, correct_word))
