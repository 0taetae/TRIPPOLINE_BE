-- 여행 계획 테이블
CREATE TABLE Plan (
      plan_id INT AUTO_INCREMENT PRIMARY KEY,
      user_id VARCHAR(50),
      plan_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      trip_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- 여행 장소 테이블
CREATE TABLE Place (
       plan_no INT AUTO_INCREMENT PRIMARY KEY,
       content_id INT,
       plan_id INT,
       visit_order INT DEFAULT 1,
       FOREIGN KEY (plan_id) REFERENCES Plan(plan_id) ON DELETE CASCADE
);