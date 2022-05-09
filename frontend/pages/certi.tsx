import { FC, useState, useEffect } from "react";
import Image from "next/image";
import logo3 from "../public/images/logo3.png";
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import Button from '@mui/material/Button';
import Checkbox from '@mui/material/Checkbox';
import Dialog from '@mui/material/Dialog';
import DialogTitle from '@mui/material/DialogTitle';
import CircularProgress from '@mui/material/CircularProgress';
import html2canvas from 'html2canvas'



const Certi: FC = () => {

 
  const [list, setList] = useState([])
  const [checked, setChecked] = useState([])
  const [ing, setIng] = useState(false)
  const [certiNum, setCertiNum] = useState('')


  // 기부 목록 받아오기
  useEffect(() => {
    setList([
      {
        org: '싸피재단',
        item: '라면',
        cnt: 100,
        day: '2022-05-09'
      },
      {
        org: '싸피',
        item: '휴지',
        cnt: 10,
        day: '2022-05-08'
      },
      {
        org: '싸피재단123',
        item: '컵라면',
        cnt: 5,
        day: '2022-05-07'
      },
      {
        org: '싸피재단1234',
        item: '생수 2L',
        cnt: 1000,
        day: '2022-05-06'
      },

    ])
  },[])

  // 체크박시 선택시
  const handleChange = (e) => {
    if (checked.includes(Number(e.target.value))){
      setChecked(checked.filter(idx => idx !== Number(e.target.value)))
    }else{
      setChecked([...checked, Number(e.target.value)])
    }
  }

  // 전체 선택
  const checkAll = () => {
    console.log('')
  }

  // 전체 해제
  const checkNotAll = () => {
    setChecked([])
  }

  // 현재 날짜
  let now = new Date()
  let year = now.getFullYear()
  let month = now.getMonth() + 1
  let date = now.getDate()


  // 버튼 클릭시
  const onClick = () => {
    setIng(true)
    setChecked(checked.sort((a, b) => a - b))
    setCertiNum(randomString(4)+'-'+randomString(4)+'-'+randomString(4)+'-'+randomString(4))
  }


  // 증명번호 생성 후, 증명서 생성
  useEffect(() => {
    if (certiNum !== ''){
      onHtmlToPng()
    }
  },[certiNum])



  // 난수 생섬 함수
  const randomString = (count) => {
    let str = '';
    for (let i = 0; i < count; i++) {
        str += Math.floor(Math.random() * 36).toString(36);
    }
    str = str.toUpperCase()
    return str
  }

  // div -> png
  const onHtmlToPng = () => {
    html2canvas(document.getElementById('div')).then(canvas=>{
      onSave(canvas.toDataURL('image/png'), '기부내역확인서.png')
    })
  }
  const onSave = (uri, filename) => {
    var link = document.createElement('a')
    document.body.appendChild(link)
    link.href = uri
    link.download = filename
    link.click()
    document.body.removeChild(link)
    setIng(false)
    setCertiNum('')
    setChecked([])
  }

  return (
    <>
      <div style={{maxWidth:'700px', minHeight:'600px', margin:'auto', textAlign: 'center'}}>
        <h1>나의 기부내역</h1>
        <br></br>

        <Button
          variant="contained" 
          style={{marginLeft:'5px'}}
          onClick={() => checkNotAll()}
        >
          전체 해제
        </Button>
        <TableContainer component={Paper}>
          <Table sx={{ maxWidth: 800 }}>
            <TableHead >
              <TableRow >
              <TableCell sx={{ width: 60 }} align="center">선택</TableCell>
                <TableCell sx={{ width: 70 }} align="center">번호</TableCell>
                <TableCell sx={{ width: 200 }}>기관명</TableCell>
                <TableCell sx={{ width: 170 }}>물품</TableCell>
                <TableCell sx={{ width: 80 }}>수량</TableCell>
                <TableCell sx={{ width: 120 }}>발송일</TableCell>
              </TableRow>
            </TableHead>
          <TableBody>
            {
              list.map((e, idx) => {
                return (
                  <TableRow key={idx}>
                    <TableCell align="center" style={{padding:'4px'}}>
                      <Checkbox 
                        checked={checked.includes(idx)}
                        onChange={handleChange}
                        name={`list-${idx}`}
                        value={idx}
                      />
                    </TableCell>
                    <TableCell align="center" >{idx+1}</TableCell>
                    <TableCell>{e.org}</TableCell>
                    <TableCell>{e.item}</TableCell>
                    <TableCell>{e.cnt}</TableCell>
                    <TableCell>{e.day}</TableCell>
                  </TableRow>
                )
              }
            )}
          </TableBody>
          </Table>
        </TableContainer>

        <Button
          variant="contained" 
          style={{marginTop:'20px'}}
          onClick={() => onClick()}
          disabled={checked.length === 0}
        >
          확인서 발급
        </Button>
      </div>

      {
        ing ?
        (
          <>
            <br /><br /><br /><br /><br /><br /><br /><br /><br />
            <div id='div' style={{width:'1000px'}}>
              <div style={{padding:'80px'}}>
                <span style={{fontSize:'20px'}}>문서확인번호: {certiNum}</span>

                <div style={{border:'1px solid black', padding:'50px', paddingBottom:'30px'}}>
                <span style={{display: 'flex', justifyContent: 'center', margin:'30px', fontSize:'40px', fontWeight:'bold'}}>기부내역 확인서</span>
                <h2>성 &nbsp; 함 : 김싸피</h2>
                <h2>연락처 : 010-1234-1234</h2>
                <h2>이메일 : ssafy@ssafy.com</h2>
                
                <br />
                <h1 style={{display: 'flex', justifyContent: 'center'}}>기부내역</h1>
                <div style={{minHeight:'500px'}}>
                  <TableContainer component={Paper} style={{display: 'flex', justifyContent: 'center'}}>
                    <Table >
                      <TableHead >
                        <TableRow >
                          <TableCell sx={{ width: 70 }} align="center" style={{padding:'12px'}}>번호</TableCell>
                          <TableCell sx={{ width: 200 }} style={{padding:'12px'}}>기관명</TableCell>
                          <TableCell sx={{ width: 170 }} style={{padding:'12px'}}>물품</TableCell>
                          <TableCell sx={{ width: 80 }} style={{padding:'12px'}}>수량</TableCell>
                          <TableCell sx={{ width: 120 }} style={{padding:'12px'}}>날짜</TableCell>
                        </TableRow>
                      </TableHead>

                    <TableBody>
                      {
                        checked.map((e, idx) => {
                          return (
                            <TableRow key={idx}>
                              <TableCell align="center" style={{padding:'12px'}}>{idx+1}</TableCell>
                              <TableCell style={{padding:'12px'}}>{list[e].org}</TableCell>
                              <TableCell style={{padding:'12px'}}>{list[e].item}</TableCell>
                              <TableCell style={{padding:'12px'}}>{list[e].cnt}</TableCell>
                              <TableCell style={{padding:'12px'}}>{list[e].day}</TableCell>
                            </TableRow>
                          )
                        }
                      )}
                    </TableBody>

                    </Table>
                  </TableContainer>
                </div>
                <h2 style={{display: 'flex', justifyContent: 'center'}}>위와 같이 기부하였음을 확인합니다.</h2>
                <h2 style={{display: 'flex', justifyContent: 'center', marginBottom:'10px'}}>{year}년 {month}월 {date}일</h2>
                <div style={{display: 'flex', justifyContent: 'center'}}>
                  <span style={{fontSize:'40px', fontWeight:'bold', marginTop:'20px', marginLeft:'90px'}}>헬프어스</span>
                  <Image 
                    src= {logo3}
                    alt=""
                    width={100}
                    height={100}
                  />
                </div>

              </div>
              </div>
            </div>
          </>
        ) : null
      }



      <Dialog open={ing}>
        <DialogTitle>
          확인서 발급중입니다.
          <CircularProgress size='20px' color="primary" style={{marginLeft:'10px'}}/>
        </DialogTitle>
      </Dialog>




    </>
  );
};

export default Certi;
