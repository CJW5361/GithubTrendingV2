import requests
from bs4 import BeautifulSoup
import json
import datetime
import schedule
import time

class GithubTrendingCrawler:
    def __init__(self):
        self.trending_url = "https://github.com/trending"
        self.api_url = "http://localhost:8080/api/repos"
        
    def crawl(self):
        try:
            response = requests.get(self.trending_url)
            response.raise_for_status()
            
            soup = BeautifulSoup(response.text, 'html.parser')
            repositories = soup.select('article.Box-row')
            
            for repo in repositories:
                # 저장소 이름과 URL
                name_element = repo.select_one('h2.h3 a')
                name = name_element.text.strip().replace('\n', '').replace(' ', '')
                url = f"https://github.com{name_element['href']}"
                
                # 설명
                description = ''
                desc_element = repo.select_one('p')
                if desc_element:
                    description = desc_element.text.strip()
                
                # 언어
                language = ''
                lang_element = repo.select_one('[itemprop="programmingLanguage"]')
                if lang_element:
                    language = lang_element.text.strip()
                
                # 스타와 포크 수 수정
                stars = 0
                forks = 0
                
                # 스타 수 가져오기
                stars_element = repo.select_one('a[href$="/stargazers"]')
                if stars_element:
                    stars_text = stars_element.text.strip().replace(',', '')
                    stars = int(stars_text)
                
                # 포크 수 가져오기
                forks_element = repo.select_one('a[href$="/forks"]')
                if forks_element:
                    forks_text = forks_element.text.strip().replace(',', '')
                    forks = int(forks_text)
                
                # API로 데이터 전송
                repo_data = {
                    "name": name,
                    "url": url,
                    "description": description,
                    "language": language,
                    "stars": stars,
                    "forks": forks
                }
                
                print(f"Crawled data: {repo_data}")  # 데이터 확인용
                self.send_to_api(repo_data)
                
        except Exception as e:
            print(f"Error during crawling: {e}")

    def convert_to_number(self, text):
        text = text.lower().strip()
        if 'k' in text:
            return int(float(text.replace('k', '')) * 1000)
        elif 'm' in text:
            return int(float(text.replace('m', '')) * 1000000)
        try:
            return int(text)
        except:
            return 0
    
    def send_to_api(self, data):
        try:
            # 문자열 데이터 전처리
            data['name'] = data['name'][:500] if data['name'] else ''
            data['url'] = data['url'][:1000] if data['url'] else ''
            data['description'] = data['description'][:2000] if data['description'] else ''
            data['language'] = data['language'][:100] if data['language'] else ''
            
            # None 값 처리
            data['stars'] = data['stars'] or 0
            data['forks'] = data['forks'] or 0
            
            response = requests.post(self.api_url, json=data)
            response.raise_for_status()
            print(f"Successfully saved repository: {data['name']}")
        except Exception as e:
            print(f"Error sending data to API: {e}")
            print(f"Failed data: {data}")  # 실패한 데이터 출력

def job():
    crawler = GithubTrendingCrawler()
    crawler.crawl()

if __name__ == "__main__":
    print("Starting GitHub Trending Crawler...")
    
    # 즉시 한 번 실행
    job()
    
    # 매시간 실행되도록 스케줄링
    schedule.every(1).hours.do(job)
    
    while True:
        schedule.run_pending()
        time.sleep(60)