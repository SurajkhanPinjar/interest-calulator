
---

# **🚀 Interest Rate Calculator API**

A high-performance financial calculation API providing industry-accurate results for:

- Simple Interest
- Compound Interest
- Loan EMI
- Loan Amortization Schedule
- SIP Future Value
- FD Maturity
- RD Maturity
- Savings Goal Projection

Designed for:

🔥 Fintech apps • 🔥 Loan comparison tools • 🔥 Personal finance websites • 🔥 Investment dashboards • 🔥 RapidAPI monetization.

---

# **🛠 Tech Stack Used**

### **Backend**

- **Java 17**
- **Spring Boot 3.4**
- **Spring Web**
- **Spring Validation**
- **Lombok**
- **Maven**

### **Security**

- API Key Authentication (X-API-KEY)
- Custom Filter Security Layer
- Optional RapidAPI Key Auto-Detection

### **Documentation**

- OpenAPI 3 (Swagger UI)
- SpringDoc 2.x

### **Deployment**

- Docker (multi-stage optimized)
- Railway.app (automated container hosting)

---

# **🔥 Why This API Is Better Than Other Finance APIs on RapidAPI**

Most existing finance APIs on RapidAPI are:

❌ Limited to only EMI or simple calculators

❌ Missing SIP, RD, FD, Amortization features

❌ Not accurate (wrong EMI or compounding formulas)

❌ No documentation

❌ No security

❌ Inconsistent JSON response

❌ Don’t support fractional years

### **💎 OUR API PROVIDES PRO-LEVEL QUALITY:**

| **Feature** | **Our API** | **Other APIs** |
| --- | --- | --- |
| Simple Interest | ✔ | ✔ |
| Compound Interest | ✔ | ✔ |
| Loan EMI | ✔ Accurate | ⚠ Often incorrect |
| Loan Amortization | ✔ Full breakdown | ❌ Rare |
| SIP Future Value | ✔ | ❌ |
| FD Maturity | ✔ | ❌ |
| RD Maturity | ✔ | ❌ |
| Savings Goal Projection | ✔ | ❌ |
| Swagger Docs | ✔ Beautiful UI | ❌ |
| API Key Security | ✔ Professional | ❌ |
| Portability | ✔ Docker + Railway | ❌ |

This makes your API one of the **most complete financial calculator APIs on RapidAPI**.

---

# **📡 Base URL**

**Local**

```
http://localhost:8082/api/v1/interest
```

**Production (Railway)**

```
https://your-app.up.railway.app/api/v1/interest
```

---

# **📌 API Endpoints + Request/Response Samples**

---

## **1️⃣ Simple Interest**

**GET** /simple

**Request**

```
/simple?principal=150000&rate=7.5&time=3&timeUnit=years
```

**Response**

```
{
  "principal": 150000,
  "rate": 7.5,
  "time": 3,
  "timeUnit": "years",
  "simpleInterest": 33750,
  "totalAmount": 183750
}
```

---

## **2️⃣ Compound Interest**

**GET** /compound

**Request**

```
/compound?principal=100000&rate=8&years=5&n=4
```

**Response**

```
{
  "principal": 100000,
  "rate": 8,
  "years": 5,
  "compoundsPerYear": 4,
  "finalAmount": 148595.89,
  "compoundInterest": 48595.89
}
```

---

## **3️⃣ Loan EMI**

**GET** /emi

**Request**

```
/emi?principal=500000&annualRate=10.5&tenureMonths=60
```

**Response**

```
{
  "principal": 500000,
  "annualRate": 10.5,
  "tenureMonths": 60,
  "monthlyEmi": 10724.98,
  "totalPayable": 643498.8,
  "totalInterest": 143498.8
}
```

---

## **4️⃣ SIP Future Value**

**GET** /sip/future-value

**Request**

```
/sip/future-value?monthlySip=5000&annualRate=12&years=10
```

**Response**

```
{
  "monthlySip": 5000,
  "annualRate": 12,
  "years": 10,
  "totalInvested": 600000,
  "futureValue": 1139793.35,
  "totalGain": 539793.35
}
```

---

## **5️⃣ FD Maturity**

**GET** /fd

**Request**

```
/fd?principal=200000&rate=7&years=3&n=4
```

**Response**

```
{
  "principal": 200000,
  "rate": 7,
  "years": 3,
  "compoundsPerYear": 4,
  "maturityAmount": 245678.91,
  "interestEarned": 45678.91
}
```

---

## **6️⃣ Recurring Deposit (RD)**

**GET** /rd

**Request**

```
/rd?monthlyDeposit=5000&rate=7.2&years=5
```

**Response**

```
{
  "monthlyDeposit": 5000,
  "rate": 7.2,
  "years": 5,
  "maturityAmount": 360492.78,
  "interestEarned": 60492.78
}
```

---

## **7️⃣ Loan Amortization Schedule**

**GET** /loan/amortization

**Request**

```
/loan/amortization?principal=500000&annualRate=10.5&tenureMonths=12
```

**Response**

```
{
  "principal": 500000,
  "annualRate": 10.5,
  "tenureMonths": 12,
  "monthlyEmi": 43954.54,
  "schedule": [
    {
      "month": 1,
      "emi": 43954.54,
      "interest": 4375,
      "principal": 39579.54,
      "remainingBalance": 460420.46
    }
  ]
}
```

---

## **8️⃣ Savings Goal (Required SIP + Lumpsum)**

**GET** /savings-goal

**Request**

```
/savings-goal?targetAmount=1000000&annualRate=12&years=10
```

**Response**

```
{
  "targetAmount": 1000000,
  "years": 10,
  "annualRate": 12,
  "requiredMonthlySip": 3941.73,
  "requiredLumpsum": 3220.64
}
```

---

# **🔥 Postman-Ready Endpoints**

```
{{baseUrl}}/simple?principal=150000&rate=7.5&time=3&timeUnit=years
{{baseUrl}}/compound?principal=100000&rate=8&years=5&n=4
{{baseUrl}}/emi?principal=500000&annualRate=10.5&tenureMonths=60
{{baseUrl}}/sip/future-value?monthlySip=5000&annualRate=12&years=10
{{baseUrl}}/fd?principal=200000&rate=7&years=3&n=4
{{baseUrl}}/rd?monthlyDeposit=5000&rate=7.2&years=5
{{baseUrl}}/loan/amortization?principal=500000&annualRate=10.5&tenureMonths=12
{{baseUrl}}/savings-goal?targetAmount=1000000&annualRate=12&years=10
```

---

# **💸 Pricing (RapidAPI)**

| **Plan** | **Price** | **Monthly Requests** | **Best For** |
| --- | --- | --- | --- |
| **Free** | $0 | 200 | Basic testing |
| **Beginner** | $1.99 | 1,000 | Students, prototypes |
| **Basic** | $4.99 | 10,000 | Hobby projects |
| **Pro** | $14.99 | 100,000 | Fintech apps |
| **Ultra** | $29.99 | 500,000 | Production SaaS |

This pricing converts VERY well because:

- Beginner tier is affordable
- Pro tier gives high value
- Ultra tier supports large fintech apps

---

# **🐳 Docker Usage**

Build:

```
docker build -t interest-api .
```

Run:

```
docker run -p 8082:8082 -e API_KEY=MY_SECRET_KEY interest-api
```

---

# **☁️ Railway Deployment**

1. Push repo to GitHub
2. Create new Railway project → Deploy Repo
3. Railway auto-detects Dockerfile
4. Add ENV variables:

```
API_KEY=YOUR_KEY
PORT=8082
```

🚀 API goes live instantly.

---

# **📞 Support**

For issues or requests: contact us on RAPIDAPI