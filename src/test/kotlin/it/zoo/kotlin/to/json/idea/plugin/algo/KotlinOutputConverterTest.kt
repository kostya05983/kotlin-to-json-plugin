package it.zoo.kotlin.to.json.idea.plugin.algo

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class KotlinOutputConverterTest {
    private val kotlinOutputConverter = KotlinOutputConverter()

    @Test
    fun `should success convert`() {
        val output = kotlinOutputConverter.convert("Test(a=ttt, a1=20, a2=30.0, t3=2021-09-19T09:54:38.162Z, test=Test2(i=2021-09-19))", "yyyy-MM-dd")
        assertEquals("{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-19\",\"test\":{\"i\":\"2021-09-19\"}}", output)
    }

    @Test
    fun `should success convert second example`() {
        val output = kotlinOutputConverter.convert("Test(a=ttt, a1=20, a2=30.0, t3=2021-09-26T15:27:25.770Z, test=Test2(i=2021-09-26), test2=Test3(test=2021-09-26T15:27:25.784))", "yyyy-MM-dd")
        assertEquals("{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-26\",\"test\":{\"i\":\"2021-09-26\"},\"test2\":{\"test\":\"2021-09-26\"}}", output)
    }

    @Test
    fun `should convert array to json`() {
        val output = kotlinOutputConverter.convert("[Test(a=ttt, a1=20, a2=30.0, t3=2021-09-28T14:28:19.037Z, test=[Test2(i=2021-09-28)], test2=Test3(test=2021-09-28T14:28:19.055)), Test(a=ttt, a1=20, a2=30.0, t3=2021-09-28T14:28:19.037Z, test=[Test2(i=2021-09-28)], test2=Test3(test=2021-09-28T14:28:19.055))]", "yyyy-MM-dd")
        assertEquals("[{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-28\",\"test\":[{\"i\":\"2021-09-28\"}],\"test2\":{\"test\":\"2021-09-28\"}},{\"a\":\"ttt\",\"a1\":20,\"a2\":30.0,\"t3\":\"2021-09-28\",\"test\":[{\"i\":\"2021-09-28\"}],\"test2\":{\"test\":\"2021-09-28\"}}]", output)
    }

    @Test
    fun `should success convert class with uuid`() {
        val output = kotlinOutputConverter.convert("Test(a=6de25b31-afec-4672-b25f-015cc091591d)", "yyyy-MM-dd")
        assertEquals("{\"a\":\"6de25b31-afec-4672-b25f-015cc091591d\"}", output)
    }

    @Test
    fun `should convert enum`() {
        val output = kotlinOutputConverter.convert("Test(a=CODE_24354235)", "yyyy-MM-dd")
        assertEquals("{\"a\":\"CODE_24354235\"}", output)
    }

    @Test
    fun `should convert array of enums`() {
        val output = kotlinOutputConverter.convert("[CODE_222, CODE_23232]", "yyyy-MM-dd")
        assertEquals("[\"CODE_222\",\"CODE_23232\"]", output)
    }

    @Test
    fun `should convert empty string`() {
        val output = kotlinOutputConverter.convert("Test(t=)", "yyyy-MM-dd")
        assertEquals("{\"t\":null}", output)
    }

    @Test
    fun `should convert array of empty string`() {
        val output = kotlinOutputConverter.convert("Test(t=,t2=)", "yyyy-MM-dd")
        assertEquals("{\"t\":null,\"t2\":null}", output)
    }

    @Test
    fun `should convert`() {
        val output = kotlinOutputConverter.convert("ElectronicMortgageRequest(declarants=[Subject(declarantId=6de25b31-afec-4672-b25f-015cc091591d, subject=SubjectRef(subjectId=6ebe1ed0-2b34-11ec-8473-6bd8b90abadb), notification=null), Subject(declarantId=c981fca7-659a-4f73-8da4-a32b41e0c7c3, subject=SubjectRef(subjectId=6ebe1ed0-2b34-11ec-8473-6bd8b90abadb), notification=null), Subject(declarantId=79306b31-6245-4880-8edb-dc9515b803c6, subject=SubjectRef(subjectId=6ebe1ed0-2b34-11ec-8473-6bd8b90abadb), notification=null)], pledgers=[DeclarantRef(declarantId=6de25b31-afec-4672-b25f-015cc091591d)], debtors=[DeclarantRef(declarantId=c981fca7-659a-4f73-8da4-a32b41e0c7c3)], pledges=[Pledge(pledge=Object(estateObject=EstateObject(objectId=c71dedc0-3625-11ec-98e1-9b38aa9e5901, objectType=FLAT, cadastralNumber=CadastralNumber(number=55:55:555555:55), address=Address(structuralAddress=StructuralAddress(region=CODE_77, district=null, city=null, urbanDistrict=null, locality=null, street=AddressElement(name=Ленинградский, abbreviation=пр-кт), house=AddressElement(name=1, abbreviation=д), building=null, structure=null, apartment=null, okato=null, oktmo=null, postalCode=null, kladr=null), note=null), area=Area(value=10.0, unit=SQUARE_METER), usageType=null), rightType=CODE_001001000000, rightRegistration=RegistrationInfo(authority=1, number=1, date=2021-10-01)), marketPrice=MarketPrice(type=MARKET, amount=Amount(value=10000000.00), reportDate=2021-10-01, evalDate=2021-10-26, valuers=[Person(name=Иван, surname=Иванов, sro=111, patronymic=Иванович)]), burden=null, mortgage=null)], mortgageTerms=MortgageTerms(obligation=Obligation(debt=Defined(cost=Cost(amount=Amount(value=1000000.00), currency=RUB)), grantingProcedure=1, baseDocuments=[Legal(document=DocumentRef(documentId=f357a273-313a-4db2-b2c5-178902b6779c))], balance=null), interests=Interests(startDate=Left(value=STANDARD), endDate=Left(value=STANDARD), rate=Rate(type=FIXED_RATE, amount=Amount(value=10.00), period=CODE_520101, variableRateConditions=null), interestPeriod=InterestPeriod(firstInterestPeriodDate=Left(value=CODE_540101), baseInterestPeriod=ru.kontur.realty.mortgage.model.terms.interests.BaseInterestPeriod\$Period@2e245065, lastPeriodIfWeekend=Left(value=CODE_570101)), accrualRules=AccrualRules(regularity=Left(value=MONTH), base=Left(value=CODE_580101), interestsRounding=Left(value=CODE_580201), intermedRounding=Left(value=CODE_580301))), debtPaymentPlan=DebtPaymentPlan(fromDate=Date(date=2022-01-30), endDate=Date(date=2022-01-30), paymentDate=Date(date=2022-02-27), paymentIfWeekend=Left(value=CODE_570101), firstPayment=FirstPayment(totalPayment=TotalPayment(amount=ru.kontur.realty.mortgage.model.terms.debt.payment.TotalPaymentAmount\$Annuity@3cf972d0, roundingType=Left(value=CODE_650301)), paymentOnDate=PaymentOnDate(amount=Cost(amount=Amount(value=100.00), currency=RUB), date=Loan(loan=true)), recalculationConditions=RecalculationConditions(conditions=[CODE_650201], description=null), term=Left(value=CODE_640301), interestPayment=InterestPayment(amount=Left(value=CODE_640202), roundingType=Left(value=CODE_650305)), principalPayment=PrincipalPayment(amount=Left(value=CODE_650401), roundingType=Left(value=CODE_650301)), otherDebtPayment=OtherDebtPayment(amount=Left(value=NOT_PAID), roundingType=Left(value=CODE_650301))), nthPayment=[NthPayment(fromPaymentNumber=PositiveNumber(number=1), toPaymentNumber=PositiveNumber(number=22), totalPayment=TotalPayment(amount=ru.kontur.realty.mortgage.model.terms.debt.payment.TotalPaymentAmount\$Annuity@3cf972d0, roundingType=Left(value=CODE_650301)), recalculationConditions=RecalculationConditions(conditions=[CODE_650201], description=null), term=Left(value=CODE_650501), interestPayment=InterestPayment(amount=Left(value=CODE_640202), roundingType=Left(value=CODE_650301)), principalPayment=PrincipalPayment(amount=Left(value=CODE_650402), roundingType=Left(value=CODE_650301)), otherDebtPayment=OtherDebtPayment(amount=Left(value=NOT_PAID), roundingType=Left(value=CODE_650301)), paymentOnDate=PaymentOnDate(amount=Cost(amount=Amount(value=10000.00), currency=RUB), date=Loan(loan=true)))], lastPayment=LastPayment(amount=Left(value=STANDARD), term=Left(value=STANDARD)), fines=Fines(interestViolation=Fixed(amount=Amount(value=111.00), payPeriod=YEAR), principalViolation=Fixed(amount=Amount(value=111.00), payPeriod=YEAR), penaltyCalculation=1, penaltyRounding=Left(value=CODE_650301))), additionalTerms=AdditionalTerms(terms=Terms(estrangement=Left(value=CODE_880101), earlyRepayment=1, partlyEarlyRepayment=1, earlyRepaymentRequest=1, paymentPlanViolation=1, other=1, punishment=, pledgeSelling=, thirdPersonUsage=null), rightsAndDuties=RightsAndDuties(debtorRights=1, debtorDuties=1, pledgeeDuties=1, pledgeeRights=1, jurisdictionOfDisputes=1, additionalCommitments=), creditPurpose=CreditPurpose(forBusiness=true, purpose=Left(value=CODE_820101)), insurance=Insurance(property=null, other=), loanAgreementEffect=null, demands=null)), depositoryAccounting=DepositoryAccounting(storage=Organization(name=111, kpp=770301001, regDate=2021-10-01, address=Address(structuralAddress=StructuralAddress(region=CODE_77, district=null, city=null, urbanDistrict=null, locality=null, street=AddressElement(name=Автозаводская, abbreviation=ул), house=AddressElement(name=1, abbreviation=д), building=null, structure=null, apartment=null, okato=null, oktmo=null, postalCode=null, kladr=null), note=null), foreignOrgParams=null, nativeOrgParams=NativeOrgParams(ogrn=5167746332364, inn=7743181857)), record=Depository(organization=Organization(name=111, kpp=770301001, regDate=2021-10-01, address=Address(structuralAddress=StructuralAddress(region=CODE_77, district=null, city=null, urbanDistrict=null, locality=null, street=AddressElement(name=Автозаводская, abbreviation=ул), house=AddressElement(name=1, abbreviation=д), building=null, structure=null, apartment=null, okato=null, oktmo=null, postalCode=null, kladr=null), note=null), foreignOrgParams=null, nativeOrgParams=NativeOrgParams(ogrn=5167746332364, inn=7743181857)), accountRequisites=AccountRequisites(accountNumber=123456789123, accountSection=null)), nominee=null, firstOwnerRequisites=AccountRequisites(accountNumber=123445, accountSection=null)), appliedDocuments=[DocumentRequest(documentId=f357a273-313a-4db2-b2c5-178902b6779c, documentInfo=DocumentInfo(documentType=CODE_558401010101, number=б/н, series=null, issueDate=2021-10-01, endingDate=null, issuer=null, placeOfIssue=1), content=ContentRequest(contentInfo=UnresolvedContentInfo(contentId=5ff1b720-3621-11ec-9ef1-614753bb7968, contentType=PDF, contentName=тест.pdf, md5=1E2B73489233F060CE96DBC37ECE3552, size=190328), signatures=[UnresolvedContentInfo(contentId=32aba690-3626-11ec-8e71-2f1693f31d38, contentType=SIG, contentName=тест.pdf.sig, md5=85da3a4f62862375c46d941b4d235c69, size=3061)]))], firstOwners=[FirstOwner(firstOwner=DeclarantRef(declarantId=79306b31-6245-4880-8edb-dc9515b803c6), firstOwnerType=CODE_359000000100)], documentaryMortgage=null)", "dd.MM.yyyy")
        println(output)
    }
}