import {SummaryItem} from "./SummaryItem";
export function Summary({ items = [] }) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0);
    return (
        <>
            <div>
                <h5 className="m-0 p-0"><b>Summary</b></h5>
            </div>
            <hr/>
                { items.map(v => <SummaryItem key={v.id} count={v.count} productName={v.productName} />) }
            <div className="row">
                <form>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">이메일</label>
                        <input type="email" className="form-control mb-1" id="email"/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="address" className="form-label">주소</label>
                        <input type="text" className="form-control mb-1" id="address"/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="postcode" className="form-label">우편번호</label>
                        <input type="text" className="form-control" id="postcode"/>
                    </div>
                    <div>당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.</div>
                </form>
                <div className="row pt-2 pb-2 border-top">
                    <h5 className="col">총금액</h5>
                    <h5 className="col text-end">{totalPrice}원</h5>
                </div>
                <button className="btn btn-dark col-12">결제하기</button>
            </div>
        </>
    )
}