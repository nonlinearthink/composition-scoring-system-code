#!/usr/bin/env python
# -*- coding: utf-8 -*-

# compute the edit distance
def edit_distance(s1, s2):
    m = len(s1)
    n = len(s2)
    # if m = 0, at least delete n character
    if m == 0:
        if n > 0:
            return n
        else:
            return 0
    # if n = 0, at least delete m character
    # if m =0,n = 0, do nothing
    if n == 0:
        if m > 0:
            return m
        else:
            return 0

    # judge from right to left
    if s1[-1] == s2[-1]:
        return edit_distance(s1[:-1], s2[:-1])
    else:
        # 1 + min(insert,delete,replace)
        return 1 + min(edit_distance(s1, s2[:-1]), edit_distance(s1[:-1], s2), edit_distance(s1[:-1], s2[:-1]))


if __name__ == '__main__':
    string1 = "Sunday"
    string2 = "Saturday"
    distance = edit_distance(string1, string2)
    print("distance：", distance)
