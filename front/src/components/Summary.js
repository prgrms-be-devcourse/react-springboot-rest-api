import {SummaryItem} from "./SummaryItem";
import React, {useState} from "react";

export function Summary({items = [], onOrderSubmit}) {
    const totalPrice = items.reduce((prev, curr) => prev + (curr.price * curr.count), 0)
    const [order, setOrder] = useState({
        email: "", address: "", postcode: ""
    });
    const changeEmailInput = (e) => { setOrder({...order, email: e.target.value}); }
    const changeAddressInput = (e) => { setOrder({...order, address: e.target.value}); }
    const changePostcodeInput = (e) => { setOrder({...order, postcode: e.target.value}); }
    const handleSubmit = (e) => {
        if (order.address === "" || order.email === "" || order.postcode === "") {
            alert("입력되지 않은 정보가 있습니다 확인해주세요.");
        } else {
            onOrderSubmit(order)
        }
    }
    return (
        <>
            <div className="col-md-4 summary p-4">
                <div>
                    <h5 className="m-0 p-0"><b>주문 정보</b></h5>
                </div>
                <hr/>
                {items.map(v => <SummaryItem key={v.productId} count={v.count} productName={v.name}/>)}
                <form>
                    <div className="mb-3">
                        <label htmlFor="email" className="form-label">이메일</label>
                        <input type="email" className="form-control mb-1" value={order.email} onChange={changeEmailInput} id="email"/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="address" className="form-label">주소</label>
                        <input type="text" className="form-control mb-1" value={order.address} onChange={changeAddressInput} id="address"/>
                    </div>
                    <div className="mb-3">
                        <label htmlFor="postcode" className="form-label">우편번호</label>
                        <input type="text" className="form-control" value={order.postcode} onChange={changePostcodeInput} id="postcode"/>
                    </div>
                </form>
                <div className="row pt-2 pb-2 border-top">
                    <h5 className="col">총금액</h5>
                    <h5 className="col text-end">{totalPrice}원</h5>
                </div>
                <button className="btn btn-dark col-12" onClick={handleSubmit}>결제하기</button>
            </div>
        </>
    )
}