import functools


def a(s):
    print(s)

    def b(f):
        print(2)

        @functools.wraps(f)
        def c(self):
            print(3)
            f(self, s+s)
        return c
    return b


class G:
    def __init__(self) -> None:
        self.th = 1

    @a("ok")
    def d(self, ss):
        print(self.th)
        print(ss)


if __name__ == "__main__":
    temp = G()
    temp.d()
