package cn.edu.zucc.se2020g11.api;

import com.github.houbb.word.checker.core.impl.EnWordChecker;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserApiApplicationTests {

	@Test
	void contextLoads() {
		long startTime = System.currentTimeMillis();
		System.out.println(EnWordChecker.getInstance().isCorrect("yoker"));
		long endTime = System.currentTimeMillis();
		System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
	}

}
