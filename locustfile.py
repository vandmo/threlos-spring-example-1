import random
from locust import HttpUser, task, between

def randomUser():
    return random.choice([
        ('admin', 'verysecret'),
        ('normaluser', 'secret'),
    ])

class QuickstartUser(HttpUser):
    wait_time = between(5, 9)

    @task
    def hello(self):
        self.client.get("/hello", auth=randomUser())

